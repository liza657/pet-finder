package com.example.petfinder.dto.lost_animal.response;

import com.example.petfinder.model.entity.Image;
import com.example.petfinder.model.enums.*;

import java.time.LocalDate;

public record AnimalView(String name,

                         String distinguishingFeatures,

                         String about,

                         String breed,

                         LocalDate foundAt,

                         Sex sex,

                         Size size,

                         Age age,

                         Type type,

                         LostAnimalStatus status,

                         byte[] image1,

                         byte[] image2,

                         byte[] image3,

                         byte[] image4) {

}
