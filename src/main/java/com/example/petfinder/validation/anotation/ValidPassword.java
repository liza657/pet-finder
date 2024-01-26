package com.example.petfinder.validation.anotation;

import com.example.petfinder.validation.validator.PasswordMatchesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE,ElementType.RECORD_COMPONENT,ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
public @interface ValidPassword {

    String message() default "Password must be at least 8 characters long, contains at least 1 upper case letter, 1 lower case letter, and 1 digit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
