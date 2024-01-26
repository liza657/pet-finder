package com.example.petfinder.service;

import respose.AnimalCard;
import com.example.petfinder.dto.animal.request.AnimalCreation;
import com.example.petfinder.dto.animal.request.AnimalUpdating;
import respose.AnimalView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.UUID;
import java.util.zip.DataFormatException;

public interface AnimalService {

    AnimalView addAnimal(AnimalCreation animalCreation, String ownerEmail) throws IOException, DataFormatException;

    AnimalView getAnimalById(UUID animalId) throws DataFormatException, IOException;

    Page<AnimalCard> getAllAnimals(Pageable pageable);


    AnimalView updateAnimal(UUID animalId, AnimalUpdating animalUpdate, String ownerEmail) throws IOException, DataFormatException;

    void deleteAnimal(UUID animalId);
}
