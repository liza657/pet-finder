package com.example.petfinder.dto.animal.request;

import com.example.petfinder.model.enums.Sex;
import com.example.petfinder.model.enums.Size;
import com.example.petfinder.model.enums.Sterilization;
import com.example.petfinder.model.enums.Type;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AnimalCreation(String name,

                             LocalDate birthday,
                             String story,

                             String breed,

                             String traits,

                             String healthHistory,
                             BigDecimal weight,


                             Sterilization sterilization,

                             Sex sex,

                             Size size,

                             Type type,

                             MultipartFile image1,

                             MultipartFile image2,

                             MultipartFile image3,

                             MultipartFile image4
) {


}
