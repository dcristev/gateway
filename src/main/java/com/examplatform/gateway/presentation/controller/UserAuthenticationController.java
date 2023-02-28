package com.examplatform.gateway.presentation.controller;

import com.examplatform.gateway.persistence.entity.User;
import com.examplatform.gateway.presentation.auth.request.LoginForm;
import com.examplatform.gateway.presentation.auth.request.RegisterForm;
import com.examplatform.gateway.presentation.auth.response.JwtResponse;
import com.examplatform.gateway.service.auth.UserAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin
@RestController
@RequiredArgsConstructor
public class UserAuthenticationController {

    private final UserAuthenticationService userAuthenticationService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<JwtResponse> loginUser(@Valid @RequestBody LoginForm loginForm) {
        return ResponseEntity.ok()
                .body(userAuthenticationService.loginUser(loginForm));
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
