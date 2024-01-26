package com.example.petfinder.validation.validator;

import com.example.petfinder.dto.user.request.SignUpRequest;
import com.example.petfinder.validation.anotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordMatchesValidator implements ConstraintValidator<ValidPassword, String> {
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[a-zA-Z\\d\\W_]+$";


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.compile(PASSWORD_PATTERN)
                .matcher(value)
                .matches();
    }
}
