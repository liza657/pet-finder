package com.example.petfinder.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`avatar`")
@Entity
@Builder
public class Avatar {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "`name`", nullable = false)
    private String name;

    @Column(name = "`type`", nullable = false)
    private String type;

    @Lob
    @Column(name = "image_data", nullable = false, length = 1000000000)
    private byte[] imageData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;


    public Avatar(String name, String type, byte[] imageData, User user) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
        this.user = user;
    }

    public Avatar(String name, String type, byte[] imageData) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
    }
}
