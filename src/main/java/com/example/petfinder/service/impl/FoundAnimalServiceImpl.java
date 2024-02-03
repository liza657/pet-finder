package com.example.petfinder.service.impl;

import com.example.petfinder.dto.found_animal.request.AnimalCreation;
import com.example.petfinder.dto.found_animal.request.AnimalUpdating;
import com.example.petfinder.dto.found_animal.response.AnimalCard;
import com.example.petfinder.dto.found_animal.response.AnimalView;
import com.example.petfinder.exceptions.EntityNotExistsException;
import com.example.petfinder.exceptions.PermissionException;
import com.example.petfinder.mapper.FoundAnimalMapper;
import com.example.petfinder.mapper.ImageMapper;
import com.example.petfinder.model.entity.FoundAnimal;
import com.example.petfinder.model.entity.Image;
import com.example.petfinder.model.entity.User;
import com.example.petfinder.model.enums.FoundAnimalStatus;
import com.example.petfinder.repository.FoundAnimalRepository;
import com.example.petfinder.repository.ImageRepository;
import com.example.petfinder.repository.UserRepository;
import com.example.petfinder.service.FoundAnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class FoundAnimalServiceImpl implements FoundAnimalService {
    private final FoundAnimalMapper animalMapper;
    private final ImageMapper imageMapper;
    private final FoundAnimalRepository animalRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    private final static String ANIMAL_NOT_FOUND = "Animal with id:%s not found";
    private final static String ANIMAL_IS_ALREADY_ADDED = "Animal is already added";
    private static final String USER_WITH_LOGIN_NOT_FOUND = "User with email:%s not found";
    private static final String PERMISSION_DENIED = "Permission denied.";

    @Override
    public AnimalView createFoundAnimal(AnimalCreation animalCreation) throws IOException, DataFormatException {
        FoundAnimal animal = animalMapper.newToFoundAnimal(animalCreation);
        animal.setUser(getCurrentUser());
        animal.setStatus(FoundAnimalStatus.FOUND);
        animal = animalRepository.save(animal);
        return animalMapper.animalToView(animal);
    }

    @Override
    public AnimalView updateFoundAnimal(UUID animalId, AnimalUpdating animalUpdating) throws DataFormatException, IOException {
        FoundAnimal animal = findAnimalById(animalId);
        checkPermission(animalId);
        copyUpdateFieldsToAnimal(animalUpdating, animal);
        animal = animalRepository.save(animal);
        return animalMapper.animalToView(animal);
    }

    @Override
    public Page<AnimalCard> getAllAnimals(Pageable pageable) {
        return animalRepository.findAll(pageable).map(animalMapper::animalToCard);
    }

    private void copyUpdateFieldsToAnimal(AnimalUpdating animalUpdating, FoundAnimal animal) throws IOException {
        Image image1 = (animalUpdating.getImage1() != null) ? imageMapper.multiPartFileToImage(animalUpdating.getImage1()) : null;
        if (image1 != null) {
            image1 = imageRepository.save(image1);
        }

        Image image2 = (animalUpdating.getImage2() != null) ? imageMapper.multiPartFileToImage(animalUpdating.getImage2()) : null;
        if (image2 != null) {
            image2 = imageRepository.save(image2);
        }

        Image image3 = (animalUpdating.getImage3() != null) ? imageMapper.multiPartFileToImage(animalUpdating.getImage3()) : null;
        if (image3 != null) {
            image3 = imageRepository.save(image3);
        }

        Image image4 = (animalUpdating.getImage4() != null) ? imageMapper.multiPartFileToImage(animalUpdating.getImage4()) : null;
        if (image4 != null) {
            image4 = imageRepository.save(image4);
        }
        animal.setName(animalUpdating.getName());
        animal.setType(animalUpdating.getType());
        animal.setAge(animalUpdating.getAge());
        animal.setAbout(animalUpdating.getAbout());
        animal.setDistinguishingFeatures(animalUpdating.getDistinguishingFeatures());
        animal.setImage1(image1);
        animal.setImage2(image2);
        animal.setImage3(image3);
        animal.setImage4(image4);
        animal.setBreed(animalUpdating.getBreed());
        animal.setSize(animalUpdating.getSize());
        animal.setSex(animalUpdating.getSex());
        animal.setAge(animalUpdating.getAge());
        animal.setStatus(animalUpdating.getStatus());
    }

    private FoundAnimal findAnimalById(UUID animalId) {
        return animalRepository.findById(animalId).orElseThrow(() -> new EntityNotExistsException(String.format(ANIMAL_NOT_FOUND, animalId)));
    }


    public User getByUsername(String username) {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException(USER_WITH_LOGIN_NOT_FOUND));

    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    private void checkPermission(UUID animalId) {
        if (!getCurrentUser().getFoundAnimals().contains(findAnimalById(animalId))) {
            throw new PermissionException(PERMISSION_DENIED);
        }
    }
}
