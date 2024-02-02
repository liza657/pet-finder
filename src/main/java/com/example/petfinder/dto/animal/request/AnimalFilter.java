package com.example.petfinder.dto.animal.request;

import com.example.petfinder.model.enums.*;
import com.example.petfinder.model.enums.Age;

public record AnimalFilter(
                           Sterilization sterilization,

                           Sex sex,

                           Size size,

                           Type type,

                           Age age) {
}
