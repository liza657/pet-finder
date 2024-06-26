package com.example.petfinder.dto.lost_animal.request;

import com.example.petfinder.model.enums.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@AllArgsConstructor
public class AnimalUpdating {
    private final String name;

    private final String distinguishingFeatures;

    private final String about;

    private final String breed;

    private final LocalDate foundAt;

    private final Sex sex;

    private final Size size;

    private final Age age;

    private final Type type;

    private final LostAnimalStatus status;

    private MultipartFile image1;

    private MultipartFile image2;

    private MultipartFile image3;

    private MultipartFile image4;


    @JsonCreator
    public AnimalUpdating(
            @JsonProperty("name") String name,
            @JsonProperty("distinguishingFeatures") String distinguishingFeatures,
            @JsonProperty("about") String about,
            @JsonProperty("breed") String breed,
            @JsonProperty("foundAt") LocalDate foundAt,
            @JsonProperty("sex") Sex sex,
            @JsonProperty("size") Size size,
            @JsonProperty("age") Age age,
            @JsonProperty("type") Type type,
            @JsonProperty("status") LostAnimalStatus status) {

        this.name = name;
        this.distinguishingFeatures = distinguishingFeatures;
        this.about = about;
        this.breed = breed;
        this.foundAt = foundAt;
        this.sex = sex;
        this.size = size;
        this.age = age;
        this.type = type;
        this.status = status;

    }


}
