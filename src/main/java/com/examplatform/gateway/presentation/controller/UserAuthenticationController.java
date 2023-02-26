package com.examplatform.gateway.presentation.controller;

import com.examplatform.gateway.persistence.entity.Role;
import com.examplatform.gateway.persistence.entity.RoleName;
import com.examplatform.gateway.persistence.entity.User;
import com.examplatform.gateway.presentation.auth.request.LoginForm;
import com.examplatform.gateway.presentation.auth.request.RegisterForm;
import com.examplatform.gateway.presentation.auth.response.JwtResponse;
import com.examplatform.gateway.service.auth.UserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;


@CrossOrigin
@RestController
@RequiredArgsConstructor
public class UserAuthenticationController {

    private final UserAuthenticationService userAuthenticationService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginForm loginForm) {

        JwtResponse jwtResponse = userAuthenticationService.loginUser(loginForm);
        Set<Role> roles = jwtResponse.getUserRole();
        String redirectUrl = null;
        if (roles.contains(new Role(RoleName.ROLE_STUDENT))) {
            redirectUrl = "http://localhost:8081/api/student";//need actual url
        } else if (roles.contains(new Role(RoleName.ROLE_PROFESSOR))) {
            redirectUrl = "http://localhost:8082/api/professor";//need actual url
        } else {
            return ResponseEntity.badRequest().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtResponse.getJwt());
        headers.add("Location", redirectUrl);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<JwtResponse> registerUser(@Valid @RequestBody RegisterForm registerForm) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userAuthenticationService.registerUser(registerForm));
    }

    @GetMapping("/api/users")
    public List<User> getUsers() {
        return userAuthenticationService.getAll();
    }
}
