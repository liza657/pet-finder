package com.example.petfinder.dto.user.response;

import com.example.petfinder.model.entity.Animal;

import java.time.LocalDate;
import java.util.List;

public record UserView(
        String firstName,

        String lastName,

        String email,

        String phoneNumber,

        LocalDate birtDate,
        List<Animal> animals) {
}
