package com.example.petfinder.model.entity;


import com.example.petfinder.model.enums.Sex;
import com.example.petfinder.model.enums.Size;
import com.example.petfinder.model.enums.Type;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
public class Animal {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthDate", nullable = false)
    private LocalDate birthDate;

    @Column(name = "weight", nullable = false)
    private BigDecimal weight;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "sterilization", nullable = false)
    private boolean sterilization;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(name = "size", nullable = false)
    private Size size;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = true)
    @JsonManagedReference
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    @JsonBackReference
    private User owner;
}
