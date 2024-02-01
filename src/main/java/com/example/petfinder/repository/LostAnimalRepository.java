package com.example.petfinder.repository;

import com.example.petfinder.model.entity.LostAnimal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LostAnimalRepository extends JpaRepository<UUID, LostAnimal> {
}
