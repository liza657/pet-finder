package com.example.petfinder.dto.user.request;

import com.example.petfinder.model.enums.Sex;
import com.example.petfinder.validation.anotation.PasswordMatching;
import com.example.petfinder.validation.anotation.ValidPassword;
import com.example.petfinder.validation.anotation.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;
@Builder
@PasswordMatching(message = "Passwords do not match")
public record SignUpRequest(
        @NotBlank(message = "first name should not be empty")
        @Size(min = 2, max = 100, message = "first name length should be minimum 2 and maximum 100 characters")
        String firstName,

        @NotBlank(message = "last name should not be empty")
        @Size(min = 2, max = 100, message = "last name length should be minimum 2 and maximum 100 characters")
        String lastName,
        @ValidEmail
        String email,

        String phoneNumber,

        String biography,
        LocalDate birthDate,
        Sex sex,
        @Size(min = 8, max = 32, message = "password length should be minimum 8 and maximum 32 characters")
        @ValidPassword
        String password,
        String matchingPassword



) {


}
