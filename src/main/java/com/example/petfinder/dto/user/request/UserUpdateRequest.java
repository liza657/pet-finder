package com.example.petfinder.dto.user.request;

import com.example.petfinder.model.enums.Sex;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        String biography,
        Sex sex

) {
}
