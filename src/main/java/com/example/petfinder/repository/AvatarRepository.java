package com.example.petfinder.repository;

import com.example.petfinder.model.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AvatarRepository extends JpaRepository<Avatar, UUID> {
    Optional<Avatar> findByName(String name);
}
