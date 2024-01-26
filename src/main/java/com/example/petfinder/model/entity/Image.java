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

    @Column(name = "`name`", nullable = false)
    private String name;

    @Column(name = "`type`", nullable = false)
    private String type;

    @Lob
    @Column(name = "image_data", length = 100000, nullable = false)
    private byte[] imageData;

    @OneToOne(mappedBy = "image")
    @JsonBackReference
    private Animal animal;

    public Image(String name, String type, byte[] imageData) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
    }
}
