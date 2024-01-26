package com.example.petfinder.service.impl;

import com.example.petfinder.dto.animal.request.AnimalCreation;
import com.example.petfinder.dto.animal.request.AnimalUpdating;
import respose.AnimalCard;
import respose.AnimalView;
import com.example.petfinder.exceptions.EntityNotExistsException;
import com.example.petfinder.exceptions.PermissionException;
import com.example.petfinder.mapper.AnimalMapper;
import com.example.petfinder.mapper.ImageMapper;
import com.example.petfinder.model.entity.Animal;
import com.example.petfinder.model.entity.Image;
import com.example.petfinder.model.entity.User;
import com.example.petfinder.repository.AnimalRepository;
import com.example.petfinder.repository.ImageRepository;
import com.example.petfinder.repository.UserRepository;
import com.example.petfinder.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    private final ImageRepository imageRepository;
    private final AnimalMapper animalMapper;

    private ImageMapper imageMapper;
    private final static String ANIMAL_NOT_FOUND = "Animal with id:%s not found";

    private static final String USER_WITH_LOGIN_NOT_FOUND = "User with email:%s not found";

    private static final String PERMISSION_DENIED = "Permission denied.";


    @Override
    public AnimalView addAnimal(AnimalCreation animalCreation, String ownerEmail) throws IOException, DataFormatException {
        Animal animal = animalMapper.newToAnimal(animalCreation);
        User user = findUserByLogin(ownerEmail);
        animal.setOwner(user);
        animal = animalRepository.save(animal);
        return animalMapper.animalToView(animal);
    }

    @Override
    public AnimalView getAnimalById(UUID animalId) throws DataFormatException, IOException {
        return animalMapper.animalToView(findAnimalById(animalId));
    }

    @Override
    public Page<AnimalCard> getAllAnimals(Pageable pageable) {
        return animalRepository.findAll(pageable).map(animalMapper::animalToCard);
    }

    @Override
    public AnimalView updateAnimal(UUID animalId, AnimalUpdating animalUpdate, String ownerEmail) throws IOException, DataFormatException {
        Animal animal = findAnimalById(animalId);
        User user = findUserByLogin(ownerEmail);
        if (!user.getEmail().equals(animal.getOwner().getEmail())) {
            throw new PermissionException(PERMISSION_DENIED);
        }
        copyUpdateFieldsToAnimal(animalUpdate, animal);
        animal = animalRepository.save(animal);
        return animalMapper.animalToView(animal);
    }

    @Override
    public void deleteAnimal(UUID animalId) {
        animalRepository.deleteById(animalId);

    }

    private Animal findAnimalById(UUID animalId) {
        return animalRepository.findById(animalId).orElseThrow(() -> new EntityNotExistsException(String.format(ANIMAL_NOT_FOUND, animalId)));
    }

    private User findUserByLogin(String ownerLogin) {
        return userRepository.findUserByEmail(ownerLogin).orElseThrow(() -> new EntityNotExistsException(String.format(USER_WITH_LOGIN_NOT_FOUND, ownerLogin)));
    }

    private void copyUpdateFieldsToAnimal(AnimalUpdating animalUpdating, Animal animal) throws IOException {
        Image image = (animalUpdating.image() != null) ? imageMapper.multiPartFileToImage(animalUpdating.image()) : null;

        if (image != null) {
            image = imageRepository.save(image);
        }
        animal.setName(animalUpdating.name());
        animal.setType(animalUpdating.type());
        animal.setWeight(animalUpdating.weight());
        animal.setBirthDate(animalUpdating.birthDate());
        animal.setImage(image);
        animal.setSterilization(animalUpdating.sterilization());
        animal.setDescription(animalUpdating.description());
        animal.setSize(animalUpdating.size());
    }

}
