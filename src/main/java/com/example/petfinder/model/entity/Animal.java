package com.example.petfinder.model.entity;


import com.example.petfinder.model.enums.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "animal")
public class Animal {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "weight", nullable = false)
    private BigDecimal weight;

    @Column(name = "story")
    private String story;

    @Column(name = "breed", nullable = true)
    private String breed;

    @Column(name = "traits", nullable = true)
    private String traits;

    @Column(name = "health_history", nullable = true)
    private String healthHistory;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

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

    @OneToOne(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Image image1;

    @OneToOne(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Image image2;

    @OneToOne(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Image image3;

    @OneToOne(mappedBy = "animal", cascade = CascadeType.ALL)
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
