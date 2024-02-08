package com.example.petfinder.model.entity;


import com.example.petfinder.model.enums.*;
import com.example.petfinder.model.enums.Age;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "animal")
public class Animal {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "weight", nullable = false)
    private BigDecimal weight;

    @Column(name = "story")
    private String story;

    @Column(name = "breed")
    private String breed;

    @Column(name = "traits")
    private String traits;

    @Column(name = "health_history")
    private String healthHistory;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "age", nullable = false)
    private Age age;


    @Enumerated(EnumType.STRING)
    @Column(name = "size", nullable = false)
    private Size size;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "sterilization", nullable = false)
    private Sterilization sterilization;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "image1_id", referencedColumnName = "id",nullable = false)
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
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    @JsonBackReference
    private User owner;

    public Animal() {

    }
}
