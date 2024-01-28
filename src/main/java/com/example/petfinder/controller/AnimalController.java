package com.example.petfinder.controller;

import com.example.petfinder.dto.animal.request.AnimalCreation;
import com.example.petfinder.dto.animal.request.AnimalUpdating;
import com.example.petfinder.dto.animal.respose.AnimalCard;
import com.example.petfinder.dto.animal.respose.AnimalView;
import com.example.petfinder.service.AnimalService;
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
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalService animalService;

    @PostMapping("create")
    public ResponseEntity<AnimalView> addAnimal(@RequestPart("animal") AnimalCreation animal,
                                                @RequestPart("image1") MultipartFile image1,
                                                @RequestPart("image2") MultipartFile image2,
                                                @RequestPart("image3") MultipartFile image3,
                                                @RequestPart("image4") MultipartFile image4) throws DataFormatException, IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                animalService.addAnimal(animal)
        );
    }

    @DeleteMapping("delete/{animalId}")
    public void deleteAnimal(@PathVariable("animalId") UUID animalId) {
        animalService.deleteAnimal(animalId);
    }

    @PutMapping("update/{animalId}")
    public ResponseEntity<AnimalView> updateAnimal(@PathVariable("animalId") UUID animalId, @RequestBody AnimalUpdating animal) throws DataFormatException, IOException {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.updateAnimal(animalId, animal));

    }


    @GetMapping("get/{animalId}")
    public ResponseEntity<AnimalView> getAnimal(@PathVariable("animalId") UUID animalId) throws DataFormatException, IOException {
        return ResponseEntity.status(HttpStatus.OK).body(animalService.getAnimalById(animalId));
    }

    @GetMapping("getAll")
    public ResponseEntity<Page<AnimalCard>> getAllAnimals(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(animalService.getAllAnimals(pageable));

    }

}
