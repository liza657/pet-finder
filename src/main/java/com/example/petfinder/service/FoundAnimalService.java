package com.example.petfinder.service;


import com.example.petfinder.dto.found_animal.request.AnimalCreation;
import com.example.petfinder.dto.found_animal.request.AnimalUpdating;
import com.example.petfinder.dto.found_animal.response.AnimalCard;
import com.example.petfinder.dto.found_animal.response.AnimalView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.UUID;
import java.util.zip.DataFormatException;

public interface FoundAnimalService {

    AnimalView createFoundAnimal(AnimalCreation animalCreation) throws IOException, DataFormatException;

    AnimalView updateFoundAnimal(UUID animalId, AnimalUpdating animalUpdating) throws DataFormatException, IOException;

    Page<AnimalCard> getAllAnimals(Pageable pageable);
}
