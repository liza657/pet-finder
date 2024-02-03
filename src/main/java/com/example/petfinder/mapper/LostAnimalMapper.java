package com.example.petfinder.mapper;

import com.example.petfinder.dto.lost_animal.request.AnimalCreation;
import com.example.petfinder.dto.lost_animal.response.AnimalCard;
import com.example.petfinder.dto.lost_animal.response.AnimalView;
import com.example.petfinder.model.entity.Image;
import com.example.petfinder.model.entity.LostAnimal;
import com.example.petfinder.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.zip.DataFormatException;

@RequiredArgsConstructor
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, ImageMapper.class})
@Slf4j
public abstract class LostAnimalMapper {
    protected  ImageMapper imageMapper;

    public AnimalCard animalToCard(LostAnimal animal) {
        return new AnimalCard(
                animal.getName(),
                animal.getAbout(),
                animal.getBreed(),
                animal.getSex(),
                animal.getSize(),
                animal.getImage1()
        );
    }

    public AnimalView animalToView(LostAnimal animal) throws DataFormatException, IOException {
        byte[] image1 = (animal.getImage1() != null) ?
                ImageUtils.decompressImage(animal.getImage1().getImageData()) : null;
        byte[] image2 = (animal.getImage2() != null) ?
                ImageUtils.decompressImage(animal.getImage2().getImageData()) : null;
        byte[] image3 = (animal.getImage1() != null) ?
                ImageUtils.decompressImage(animal.getImage1().getImageData()) : null;
        byte[] image4 = (animal.getImage1() != null) ?
                ImageUtils.decompressImage(animal.getImage1().getImageData()) : null;
        return new AnimalView(
                animal.getName(),
                animal.getBreed(),
                animal.getAbout(),
                animal.getDistinguishingFeatures(),
                animal.getFoundAt(),
                animal.getSex(),
                animal.getSize(),
                animal.getAge(),
                animal.getType(),
                animal.getStatus(),
                image1,
                image2,
                image3,
                image4
        );
    }

    public LostAnimal newToLostAnimal(AnimalCreation animalCreation) throws IOException {

        Image image1 = (animalCreation.getImage1() != null)
                ? imageMapper.multiPartFileToImage(animalCreation.getImage1()) : null;

        Image image2 = (animalCreation.getImage2() != null)
                ? imageMapper.multiPartFileToImage(animalCreation.getImage2()) : null;

        Image image3 = (animalCreation.getImage3() != null)
                ? imageMapper.multiPartFileToImage(animalCreation.getImage3()) : null;

        Image image4 = (animalCreation.getImage4() != null)
                ? imageMapper.multiPartFileToImage(animalCreation.getImage4()) : null;

        LostAnimal animal = new LostAnimal();
        animal.setName(animalCreation.getName());
        animal.setBreed(animalCreation.getBreed());
        animal.setSize(animalCreation.getSize());
        animal.setType(animalCreation.getType());
        animal.setAbout(animalCreation.getAbout());
        animal.setFoundAt(animalCreation.getFoundAt());
        animal.setDistinguishingFeatures(animalCreation.getDistinguishingFeatures());
        animal.setSex(animalCreation.getSex());
        animal.setAge(animalCreation.getAge());
        animal.setImage1(image1);
        animal.setImage2(image2);
        animal.setImage3(image3);
        animal.setImage4(image4);
        return animal;
    }

    @Autowired
    public void setImageMapper(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }
}
