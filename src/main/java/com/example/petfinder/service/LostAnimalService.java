package com.example.petfinder.service;

import com.example.petfinder.dto.lost_animal.request.AnimalCreation;
import com.example.petfinder.dto.lost_animal.request.AnimalUpdating;
import com.example.petfinder.dto.lost_animal.response.AnimalCard;
import com.example.petfinder.dto.lost_animal.response.AnimalView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.UUID;
import java.util.zip.DataFormatException;

public interface LostAnimalService {
    AnimalView createLostAnimal(AnimalCreation animalCreation) throws IOException, DataFormatException;

    AnimalView updateLostAnimal(UUID animalId, AnimalUpdating animalUpdating) throws DataFormatException, IOException;

    Page<AnimalCard> getAllAnimals(Pageable pageable);


}
