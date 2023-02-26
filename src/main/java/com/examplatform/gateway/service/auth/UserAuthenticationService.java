package com.examplatform.gateway.service.auth;

import com.examplatform.gateway.persistence.entity.Role;
import com.examplatform.gateway.persistence.entity.User;
import com.examplatform.gateway.persistence.repository.RoleRepository;
import com.examplatform.gateway.persistence.repository.UserRepository;
import com.examplatform.gateway.presentation.advice.exception.UserAlreadyExistsException;
import com.examplatform.gateway.presentation.auth.request.LoginForm;
import com.examplatform.gateway.presentation.auth.request.RegisterForm;
import com.examplatform.gateway.presentation.auth.response.JwtResponse;
import com.examplatform.gateway.presentation.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtil tokenUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public JwtResponse loginUser(LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String token = tokenUtil.generateToken(userDetails.getUsername());

        return JwtResponse.builder()
                .jwt(token)
                .id(userDetails.getId())
                .fullName(userDetails.getFullName())
                .username(userDetails.getUsername())
                .userRole(userDetails.getUserRoles())
                .build();
    }

    public JwtResponse registerUser(RegisterForm registerForm) {
        if (userRepository.existsByUsername(registerForm.getUsername()))
            throw new UserAlreadyExistsException("User already exists with username " + registerForm.getUsername());

        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.findById(1L).orElseThrow());

        User user = User.builder()
                .fullName(registerForm.getFullName())
                .username(registerForm.getUsername())
                .password(passwordEncoder.encode(registerForm.getPassword()))
                .role(roles)
                .build();

        userRepository.save(user);

        LoginForm loginForm = LoginForm.builder()
                .username(registerForm.getUsername())
                .password(registerForm.getPassword())
                .build();

        return this.loginUser(loginForm);
    }

    public static UserDetailsImpl getAuthenticatedUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }
}
