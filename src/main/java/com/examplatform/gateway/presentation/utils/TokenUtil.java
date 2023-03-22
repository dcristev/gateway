package com.examplatform.gateway.presentation.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtil {

    private final Long tokenExpiration = 86400L;
    private final String secretKey = "jwt-tokens-that-should-be-changed-production";

    public String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1000 * tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validate(String jwt) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public String getUsernameFromToken(String jwt) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }
}
