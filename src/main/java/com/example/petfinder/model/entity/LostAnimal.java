package com.example.petfinder.model.entity;

import com.example.petfinder.model.enums.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "lost_animal")
@NoArgsConstructor
public class LostAnimal {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "distinguishing_features")
    private String distinguishingFeatures;

    @Column(name = "about", nullable = false)
    private String about;

    @Column(name = "breed")
    private String breed;

    @Column(name = "found_at")
    private LocalDate foundAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "size", nullable = false)
    private Size size;

    @Enumerated(EnumType.STRING)
    @Column(name = "age")
    private Age age;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LostAnimalStatus status;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "image1_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private Image image1;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image2_id", referencedColumnName = "id")
    @JsonManagedReference
    private Image image2;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image3_id", referencedColumnName = "id")
    @JsonManagedReference
    private Image image3;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image4_id", referencedColumnName = "id")
    @JsonManagedReference
    private Image image4;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    @JsonBackReference
    private User user;

}
