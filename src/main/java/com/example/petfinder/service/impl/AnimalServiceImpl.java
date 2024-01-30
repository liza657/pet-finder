package com.example.petfinder.service.impl;

import com.example.petfinder.dto.animal.request.AnimalCreation;
import com.example.petfinder.dto.animal.request.AnimalFilter;
import com.example.petfinder.dto.animal.request.AnimalUpdating;
import com.example.petfinder.dto.animal.respose.AnimalCard;
import com.example.petfinder.dto.animal.respose.AnimalView;
import com.example.petfinder.exceptions.EntityIsAlreadyExists;
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
import com.example.petfinder.specification.AnimalSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final AnimalMapper animalMapper;
    private final ImageMapper imageMapper;
    private final static String ANIMAL_NOT_FOUND = "Animal with id:%s not found";
    private final static String ANIMAL_IS_ALREADY_ADDED = "Animal is already added";
    private static final String USER_WITH_LOGIN_NOT_FOUND = "User with email:%s not found";
    private static final String PERMISSION_DENIED = "Permission denied.";


    @Override
    public AnimalView addAnimal(AnimalCreation animalCreation) throws IOException, DataFormatException {
        User user = getCurrentUser();
        Animal animal = animalMapper.newToAnimal(animalCreation);
        log.info(user.getEmail());
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

    @Override
    public void addToFavorite(UUID animalId) {
        User user = getCurrentUser();
        Animal animal = findAnimalById(animalId);
        if (!user.getFavoriteAnimals().contains(animal)) {
            user.getFavoriteAnimals().add(animal);
            userRepository.save(user);
        } else throw new EntityIsAlreadyExists(ANIMAL_IS_ALREADY_ADDED);

    }

    @Override
    public void deleteFromFavorite(UUID animalId) {
        User user = getCurrentUser();
        Animal animal = findAnimalById(animalId);
        user.getFavoriteAnimals().remove(animal);
        userRepository.save(user);

    }

    @Override
    public Set<AnimalCard> getFavoriteAnimals() {
        User user = getCurrentUser();
        return user.getFavoriteAnimals().stream()
                .map(animalMapper::animalToCard)
                .collect(Collectors.toSet());

    }

    @Override
    public Page<AnimalCard> searchAnimals(AnimalFilter filter, Integer pageNumber) {
        Specification<Animal> specification = AnimalSpecification.filterBy(filter);
        Page<Animal> pageResult = animalRepository.findAll(specification, PageRequest.of(pageNumber, 20));
        return pageResult.map(animalMapper::animalToCard);
    }

    private Animal findAnimalById(UUID animalId) {
        return animalRepository.findById(animalId).orElseThrow(() -> new EntityNotExistsException(String.format(ANIMAL_NOT_FOUND, animalId)));
    }


    private void copyUpdateFieldsToAnimal(AnimalUpdating animalUpdating, Animal animal) throws IOException {
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
        animal.setWeight(animalUpdating.getWeight());
        animal.setAge(animalUpdating.getAge());
        animal.setImage1(image1);
        animal.setImage2(image2);
        animal.setImage3(image3);
        animal.setImage4(image4);
        animal.setSterilization(animalUpdating.getSterilization());
        animal.setStory(animalUpdating.getStory());
        animal.setBreed(animalUpdating.getBreed());
        animal.setTraits(animalUpdating.getTraits());
        animal.setHealthHistory(animalUpdating.getHealthHistory());
        animal.setSize(animalUpdating.getSize());
    }


    public User getByUsername(String username) {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException(USER_WITH_LOGIN_NOT_FOUND));

    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    private void checkPermission(UUID animalId) {
        if (!getCurrentUser().getAnimals().contains(findAnimalById(animalId))) {
            throw new PermissionException(PERMISSION_DENIED);
        }
    }


}
