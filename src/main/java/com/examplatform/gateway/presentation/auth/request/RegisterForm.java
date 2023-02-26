package com.examplatform.gateway.presentation.auth.request;

import com.examplatform.gateway.presentation.auth.request.validation.MatchingPasswords;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@MatchingPasswords
@Builder
public class RegisterForm {

    @NotBlank(message = "Full Name must not be blank!")
    @Size(max = 50, message = "Full Name must be maximum 50 characters!")
    private String fullName;

    @Email(message = "Invalid email format!")
    @NotBlank(message = "Username must not be blank!")
    @Size(min = 3, max = 40, message = "Username must be between 3 and 40 characters!")
    private String username;

    @NotBlank(message = "Password must not be blank!")
    @Size(min = 8, max = 25, message = "Password must be between 8 and 25 characters!")
    private String password;

    @NotBlank(message = "ConfirmPassword must not be blank!")
    @Size(min = 8, max = 25, message = "Confirm Password must be between 8 and 25 characters!")
    private String confirmPassword;
}
