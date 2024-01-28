package com.example.petfinder.dto.user.response;

public record UserCard(
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        byte[] avatar
) {
}