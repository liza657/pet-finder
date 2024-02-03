package com.example.petfinder.controller;


import com.example.petfinder.dto.found_animal.request.AnimalCreation;
import com.example.petfinder.dto.found_animal.request.AnimalUpdating;
import com.example.petfinder.dto.found_animal.response.AnimalCard;
import com.example.petfinder.dto.found_animal.response.AnimalView;
import com.example.petfinder.service.FoundAnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("animals/found")
@RequiredArgsConstructor
public class FoundAnimalController {
    private final FoundAnimalService animalService;

    @PostMapping("create")
    public ResponseEntity<AnimalView> createFoundAnimal(@RequestPart("animal") AnimalCreation animal,
                                                       @RequestPart("image1") MultipartFile image1,
                                                       @RequestPart("image2") MultipartFile image2,
                                                       @RequestPart("image3") MultipartFile image3,
                                                       @RequestPart("image4") MultipartFile image4) throws DataFormatException, IOException {
        animal.setImage1(image1);
        animal.setImage2(image2);
        animal.setImage3(image3);
        animal.setImage4(image4);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                animalService.createFoundAnimal(animal)
        );
    }

    @GetMapping("getAll")
    public ResponseEntity<Page<AnimalCard>> getAllAnimals(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(animalService.getAllAnimals(pageable));
    }

    @PutMapping("update/{animalId}")
    public ResponseEntity<AnimalView> updateAnimal(@PathVariable("animalId") UUID animalId,
                                                   @RequestPart("animal") AnimalUpdating animal,
                                                   @RequestPart("image1") MultipartFile image1,
                                                   @RequestPart("image2") MultipartFile image2,
                                                   @RequestPart("image3") MultipartFile image3,
                                                   @RequestPart("image4") MultipartFile image4) throws DataFormatException, IOException {
        animal.setImage1(image1);
        animal.setImage2(image2);
        animal.setImage3(image3);
        animal.setImage4(image4);
        return ResponseEntity.status(HttpStatus.OK).body(animalService.updateFoundAnimal(animalId, animal));

    }
}
