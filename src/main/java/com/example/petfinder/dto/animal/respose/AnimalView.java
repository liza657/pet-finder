package com.example.petfinder.dto.animal.respose;

import com.example.petfinder.model.enums.Sex;
import com.example.petfinder.model.enums.Size;
import com.example.petfinder.model.enums.Sterilization;
import com.example.petfinder.model.enums.Type;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AnimalView(String name,

                         LocalDate birthday,

                         BigDecimal weight,

                         String story,

                         String breed,

                         String traits,

                         String healthHistory,

                         Sterilization sterilization,

                         Sex sex,

                         Size size,

                         Type type,

                         byte[] image1,

                         byte[] image2,

                         byte[] image3,

                         byte[] image4

) {


}