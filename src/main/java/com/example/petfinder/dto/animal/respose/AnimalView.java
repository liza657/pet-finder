package com.example.petfinder.dto.animal.respose;

import com.example.petfinder.model.enums.*;

import java.math.BigDecimal;

public record AnimalView(String name,

                         String age,

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
