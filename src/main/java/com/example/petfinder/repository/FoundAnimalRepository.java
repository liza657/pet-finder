package com.example.petfinder.repository;

import com.example.petfinder.model.entity.FoundAnimal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FoundAnimalRepository extends JpaRepository<FoundAnimal, UUID> {
}
