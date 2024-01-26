package com.example.petfinder.dto.user.request;

import com.example.petfinder.validation.anotation.ValidPassword;
import jakarta.validation.constraints.Size;

public record PasswordUpdateRequest(String oldPassword,
                                    @ValidPassword
                                    @Size(min = 8, max = 32, message = "password length should be minimum 8 and maximum 32 characters")
                                    String newPassword) {
}
