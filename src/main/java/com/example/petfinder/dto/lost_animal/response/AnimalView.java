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

                         Status status,

                         Image image1,

                         Image image2,

                         Image image3,

                         Image image4) {

}
