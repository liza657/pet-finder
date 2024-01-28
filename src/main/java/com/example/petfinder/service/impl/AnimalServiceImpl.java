package com.example.petfinder.service.impl;

import com.example.petfinder.dto.animal.request.AnimalCreation;
import com.example.petfinder.dto.animal.request.AnimalUpdating;
import com.example.petfinder.dto.animal.respose.AnimalCard;
import com.example.petfinder.dto.animal.respose.AnimalView;
import com.example.petfinder.exceptions.EntityNotExistsException;
import com.example.petfinder.exceptions.PermissionException;
import com.example.petfinder.mapper.AnimalMapper;
import com.example.petfinder.mapper.ImageMapper;
import com.example.petfinder.model.entity.Animal;
import com.example.petfinder.model.entity.Image;
import com.example.petfinder.model.entity.User;
import com.example.petfinder.model.enums.Status;
import com.example.petfinder.repository.AnimalRepository;
import com.example.petfinder.repository.ImageRepository;
import com.example.petfinder.repository.UserRepository;
import com.example.petfinder.service.AnimalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    public AnimalView addAnimal(AnimalCreation animalCreation) throws IOException, DataFormatException {
        User user = getCurrentUser();
        Animal animal = animalMapper.newToAnimal(animalCreation);

        animal.setOwner(user);
        animal.setStatus(Status.NEED_ADOPTION);
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
    public AnimalView updateAnimal(UUID animalId, AnimalUpdating animalUpdate) throws IOException, DataFormatException {
        checkPermission(animalId);

        Animal animal = findAnimalById(animalId);
        copyUpdateFieldsToAnimal(animalUpdate, animal);
        animal = animalRepository.save(animal);
        return animalMapper.animalToView(animal);
    }

    @Override
    public void deleteAnimal(UUID animalId) {
        checkPermission(animalId);
        animalRepository.deleteById(animalId);

    }

    private Animal findAnimalById(UUID animalId) {
        return animalRepository.findById(animalId).orElseThrow(() -> new EntityNotExistsException(String.format(ANIMAL_NOT_FOUND, animalId)));
    }


    private void copyUpdateFieldsToAnimal(AnimalUpdating animalUpdating, Animal animal) throws IOException {
        Image image = (animalUpdating.image() != null) ? imageMapper.multiPartFileToImage(animalUpdating.image()) : null;

        if (image != null) {
            image = imageRepository.save(image);
        }
        animal.setName(animalUpdating.name());
        animal.setType(animalUpdating.type());
        animal.setWeight(animalUpdating.weight());
        animal.setBirthday(animalUpdating.birthday());
        animal.setImage1(image);
        animal.setImage2(image);
        animal.setImage3(image);
        animal.setImage4(image);
        animal.setSterilization(animalUpdating.sterilization());
        animal.setStory(animalUpdating.story());
        animal.setBreed(animalUpdating.breed());
        animal.setTraits(animalUpdating.traits());
        animal.setHealthHistory(animalUpdating.healthHistory());
        animal.setSize(animalUpdating.size());
    }


    public User getByUsername(String username) {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    private void checkPermission(UUID animalId) {
        if (!getCurrentUser().getAnimals().contains(findAnimalById(animalId))) {
            throw new PermissionException();
        }
    }

}
