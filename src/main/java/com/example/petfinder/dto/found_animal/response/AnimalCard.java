package com.example.petfinder.dto.found_animal.response;

import com.example.petfinder.model.entity.Image;
import com.example.petfinder.model.enums.Sex;
import com.example.petfinder.model.enums.Size;

public record AnimalCard(String name,

                         String about,

                         String breed,

                         Sex sex,

                         Size size,

                         Image image1) {


}
