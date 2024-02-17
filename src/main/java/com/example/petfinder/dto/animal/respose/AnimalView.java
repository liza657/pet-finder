package com.example.petfinder.dto.animal.respose;

import com.example.petfinder.model.enums.*;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AnimalView(String name,

                         Age age,

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
