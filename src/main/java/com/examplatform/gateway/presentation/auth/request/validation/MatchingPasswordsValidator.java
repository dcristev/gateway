package com.examplatform.gateway.presentation.auth.request.validation;

import com.examplatform.gateway.presentation.auth.request.RegisterForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchingPasswordsValidator implements ConstraintValidator<MatchingPasswords, RegisterForm> {

    @Override
    public void initialize(MatchingPasswords constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegisterForm registerForm, ConstraintValidatorContext constraintValidatorContext) {

        if(registerForm.getPassword() == null || registerForm.getConfirmPassword() == null)
            return false;
        return registerForm.getPassword().equals(registerForm.getConfirmPassword());
    }
}
