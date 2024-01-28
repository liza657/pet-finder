package com.example.petfinder.mapper;

import com.example.petfinder.dto.animal.request.AnimalCreation;
import com.example.petfinder.dto.animal.respose.AnimalCard;
import com.example.petfinder.dto.animal.respose.AnimalView;
import com.example.petfinder.model.entity.Animal;
import com.example.petfinder.model.entity.Image;
import com.example.petfinder.repository.ImageRepository;
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
public abstract class AnimalMapper {

    protected UserMapper userMapper;
    protected ImageMapper imageMapper;
    protected ImageRepository imageRepository;

    public abstract AnimalCard animalToCard(Animal animal);

    public AnimalView animalToView(Animal animal) throws DataFormatException, IOException {

        byte[] image1 = (animal.getImage1() != null) ?
                ImageUtils.decompressImage(animal.getImage1().getImageData()) : null;
        byte[] image2 = (animal.getImage1() != null) ?
                ImageUtils.decompressImage(animal.getImage1().getImageData()) : null;
        byte[] image3 = (animal.getImage1() != null) ?
                ImageUtils.decompressImage(animal.getImage1().getImageData()) : null;
        byte[] image4 = (animal.getImage1() != null) ?
                ImageUtils.decompressImage(animal.getImage1().getImageData()) : null;

        return new AnimalView(
                animal.getName(),
                animal.getBirthday(),
                animal.getWeight(),
                animal.getStory(),
                animal.getBreed(),
                animal.getTraits(),
                animal.getHealthHistory(),
                animal.getSterilization(),
                animal.getSex(),
                animal.getSize(),
                animal.getType(),
                image1,
                image2,
                image3,
                image4
        );
    }

    public Animal newToAnimal(AnimalCreation animalCreation) throws IOException {
        Animal animal = new Animal();

        Image image1 = (animalCreation.image1() != null)
                ? imageMapper.multiPartFileToImage(animalCreation.image1()) : null;

        Image image2 = (animalCreation.image1() != null)
                ? imageMapper.multiPartFileToImage(animalCreation.image1()) : null;

        Image image3 = (animalCreation.image1() != null)
                ? imageMapper.multiPartFileToImage(animalCreation.image1()) : null;

        Image image4 = (animalCreation.image1() != null)
                ? imageMapper.multiPartFileToImage(animalCreation.image1()) : null;

        animal.setName(animalCreation.name());
        animal.setBirthday(animalCreation.birthday());
        animal.setBreed(animal.getBreed());
        animal.setHealthHistory(animal.getHealthHistory());
        animal.setTraits(animal.getTraits());
        animal.setStory(animalCreation.story());
        animal.setSex(animalCreation.sex());
        animal.setSize(animalCreation.size());
        animal.setWeight(animalCreation.weight());
        animal.setType(animalCreation.type());
        animal.setSterilization(animalCreation.sterilization());
        animal.setImage1(image1);
        animal.setImage2(image2);
        animal.setImage3(image3);
        animal.setImage4(image4);
        return animal;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

}
