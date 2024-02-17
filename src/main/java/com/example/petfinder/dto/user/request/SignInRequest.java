package com.example.petfinder.dto.user.request;

import lombok.Builder;

@Builder
public record SignInRequest(String email, String password) {
}
