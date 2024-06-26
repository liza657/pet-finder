package com.example.petfinder.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`type`")
    private String type;

    @Lob
    @Column(name = "image_data", length = 100000)
    private byte[] imageData;

    @OneToOne(mappedBy = "image1")
    @JsonBackReference
    private Animal animal1;

    @OneToOne(mappedBy = "image2")
    @JsonBackReference
    private Animal animal2;

    public Image(String name, String type, byte[] imageData) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
    }
}
