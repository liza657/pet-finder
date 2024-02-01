package com.example.petfinder.model.entity;

import com.example.petfinder.model.enums.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "found_animal")
@NoArgsConstructor
public class LostAnimal {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "distinguishing_features", nullable = true)
    private String  distinguishingFeatures;

    @Column(name = "about", nullable = false)
    private String about;

    @Column(name = "breed", nullable = true)
    private String breed;

    @Column(name = "found_at", nullable = true)
    private LocalDate foundAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "size", nullable = false)
    private Size size;

    @Enumerated(EnumType.STRING)
    @Column(name = "age", nullable = false)
    private Age age;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AdvertStatus status;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "image1_id", referencedColumnName = "id", nullable = true)
    @JsonManagedReference
    private Image image1;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "image2_id", referencedColumnName = "id", nullable = true)
    @JsonManagedReference
    private Image image2;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "image3_id", referencedColumnName = "id", nullable = true)
    @JsonManagedReference
    private Image image3;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "image4_id", referencedColumnName = "id", nullable = true)
    @JsonManagedReference
    private Image image4;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    @JsonBackReference
    private User user;

}
