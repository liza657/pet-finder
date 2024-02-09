package com.example.petfinder.controller;

import com.example.petfinder.dto.lost_animal.request.AnimalCreation;
import com.example.petfinder.dto.lost_animal.request.AnimalUpdating;
import com.example.petfinder.dto.lost_animal.response.AnimalCard;
import com.example.petfinder.dto.lost_animal.response.AnimalView;
import com.example.petfinder.service.LostAnimalService;
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
@RequiredArgsConstructor
@RequestMapping("/animals/lost")
public class LostAnimalController {

    private final LostAnimalService animalService;

    @PostMapping()
    public ResponseEntity<AnimalView> createLostAnimal(@RequestPart(name = "animal") AnimalCreation animal,
                                                       @RequestPart(name = "image1", required = false) MultipartFile image1,
                                                       @RequestPart(name = "image2", required = false) MultipartFile image2,
                                                       @RequestPart(name = "image3", required = false) MultipartFile image3,
                                                       @RequestPart(name = "image4", required = false) MultipartFile image4) throws DataFormatException, IOException {
        animal.setImage1(image1);
        animal.setImage2(image2);
        animal.setImage3(image3);
        animal.setImage4(image4);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                animalService.createLostAnimal(animal)
        );
    }

    @GetMapping()
    public ResponseEntity<Page<AnimalCard>> getAllAnimals(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(animalService.getAllAnimals(pageable));
    }

    @PutMapping("{animalId}")
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
        return ResponseEntity.status(HttpStatus.OK).body(animalService.updateLostAnimal(animalId, animal));

    }

}
