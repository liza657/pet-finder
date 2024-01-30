package com.example.petfinder.dto.animal.respose;

import com.example.petfinder.model.entity.Image;
import com.example.petfinder.model.enums.Age;
import com.example.petfinder.model.enums.Sex;
import com.example.petfinder.model.enums.Sterilization;
import com.example.petfinder.model.enums.Type;

import java.math.BigDecimal;

public record AnimalCard(String name,

                         Age age,

                         BigDecimal weight,

                         Sterilization sterilization,

                         Sex sex,

                         Type type,

                         Image image) {


}
