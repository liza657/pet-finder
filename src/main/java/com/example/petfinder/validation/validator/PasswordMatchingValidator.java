package com.example.petfinder.validation.validator;

import com.example.petfinder.dto.user.request.SignUpRequest;
import com.example.petfinder.exceptions.PasswordMatchingException;
import com.example.petfinder.validation.anotation.PasswordMatching;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchingValidator implements ConstraintValidator<PasswordMatching, SignUpRequest> {

    @Override
    public void initialize(PasswordMatching constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(SignUpRequest signUpRequest, ConstraintValidatorContext context) {
        if (signUpRequest.password() == null || signUpRequest.matchingPassword() == null) {
            throw new PasswordMatchingException("Passwords cannot be null");
        }

        if (!signUpRequest.password().equals(signUpRequest.matchingPassword())) {
            throw new PasswordMatchingException("Passwords do not match");
        }

        return true;
    }
}


//    @Override
//    public boolean isValid(SignUpRequest signUpRequest, ConstraintValidatorContext constraintValidatorContext) {
//        if (signUpRequest.password() == null || signUpRequest.matchingPassword() == null) {
//            return false;
//        }
//
//        if (!signUpRequest.password().equals(signUpRequest.matchingPassword())) {
//            throw new PasswordMatchingException("Passwords don't match");
//        }
//
//        return true;
//    }
