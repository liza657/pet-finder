package com.example.petfinder.dto.animal.request;

import com.example.petfinder.model.enums.*;
import com.example.petfinder.model.enums.Age;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class AnimalCreation {
    private final String name;

    private final Age age;
    private final String story;

    private final String breed;

    private final String traits;

    private final String healthHistory;
    private final BigDecimal weight;

    private final Sterilization sterilization;

    private final Sex sex;

    private final Size size;

    private final Type type;
    private MultipartFile image1;
    private MultipartFile image2;
    private MultipartFile image3;
    private MultipartFile image4;

    @JsonCreator
    public AnimalCreation(
            @JsonProperty("name") String name,
            @JsonProperty("age") Age age,
            @JsonProperty("story") String story,
            @JsonProperty("breed") String breed,
            @JsonProperty("traits") String traits,
            @JsonProperty("healthHistory") String healthHistory,
            @JsonProperty("weight") BigDecimal weight,
            @JsonProperty("sterilization") Sterilization sterilization,
            @JsonProperty("sex") Sex sex,
            @JsonProperty("size") Size size,
            @JsonProperty("type") Type type) {

        this.name = name;
        this.age = age;
        this.story = story;
        this.breed = breed;
        this.traits = traits;
        this.healthHistory = healthHistory;
        this.weight = weight;
        this.sterilization = sterilization;
        this.sex = sex;
        this.size = size;
        this.type = type;

    }

    public void setImage1(MultipartFile image1) {
        this.image1 = image1;
    }

    public void setImage2(MultipartFile image2) {
        this.image2 = image2;
    }

    public void setImage3(MultipartFile image3) {
        this.image3 = image3;
    }

    public void setImage4(MultipartFile image4) {
        this.image4 = image4;
    }

}
