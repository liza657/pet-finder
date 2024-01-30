package com.example.petfinder.service;

import com.example.petfinder.dto.animal.request.AnimalCreation;
import com.example.petfinder.dto.animal.request.AnimalFilter;
import com.example.petfinder.dto.animal.request.AnimalUpdating;
import com.example.petfinder.dto.animal.respose.AnimalCard;
import com.example.petfinder.dto.animal.respose.AnimalView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.zip.DataFormatException;

public interface AnimalService {

    AnimalView addAnimal(AnimalCreation animalCreation) throws IOException, DataFormatException;

    AnimalView getAnimalById(UUID animalId) throws DataFormatException, IOException;

    Page<AnimalCard> getAllAnimals(Pageable pageable);


    AnimalView updateAnimal(UUID animalId, AnimalUpdating animalUpdate) throws IOException, DataFormatException;

    void deleteAnimal(UUID animalId);

    void addToFavorite(UUID animalId);

    void deleteFromFavorite(UUID animalId);

    Set<AnimalCard> getFavoriteAnimals();

    Page<AnimalCard> searchAnimals(AnimalFilter filter, Integer pageNumber);
}
