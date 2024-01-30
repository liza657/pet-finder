package com.example.petfinder.dto.animal.request;

import com.example.petfinder.model.enums.*;

import java.time.LocalDate;

public record AnimalFilter(
                           Sterilization sterilization,

                           Sex sex,

                           Size size,

                           Type type,

                           LocalDate birthday) {
}
