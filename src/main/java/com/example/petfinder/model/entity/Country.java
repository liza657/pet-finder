package com.example.petfinder.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Country {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "shortname", length = 2)
    private String shortname;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "phone_code")
    private int phoneCode;

    @OneToMany(
            mappedBy = "country",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<City> cities;
}
