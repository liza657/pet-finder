package com.example.petfinder.repository;

import com.example.petfinder.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}
