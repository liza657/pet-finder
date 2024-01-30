package com.example.petfinder.repository;

import com.example.petfinder.model.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AnimalRepository extends JpaRepository<Animal, UUID>, JpaSpecificationExecutor<Animal> {
}
