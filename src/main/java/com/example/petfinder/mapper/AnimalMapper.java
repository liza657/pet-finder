package com.example.petfinder.mapper;

import com.example.petfinder.dto.animal.request.AnimalCreation;
import respose.AnimalCard;
import respose.AnimalView;
import com.example.petfinder.dto.user.response.UserCard;
import com.example.petfinder.model.entity.Animal;
import com.example.petfinder.model.entity.Image;
import com.example.petfinder.repository.ImageRepository;
import com.example.petfinder.utils.ImageUtils;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.io.IOException;
import java.util.zip.DataFormatException;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, ImageMapper.class})
public abstract class AnimalMapper {

    protected UserMapper userMapper;
    protected ImageMapper imageMapper;
    protected ImageRepository imageRepository;

    public abstract AnimalCard animalToCard(Animal animal);

    public AnimalView animalToView(Animal animal) throws DataFormatException, IOException {
        UserCard user = userMapper.userToCard(animal.getOwner());
        byte[] image = (animal.getImage() != null) ?
                ImageUtils.decompressImage(animal.getImage().getImageData()) : null;
        return new AnimalView(
                animal.getName(),
                animal.getBirthDate(),
                animal.getWeight(),
                animal.getDescription(),
                animal.isSterilization(),
                animal.getSex(),
                animal.getSize(),
                animal.getType(),
                image,
                user);
    }

    public Animal newToAnimal(AnimalCreation animalCreation) throws IOException {
        Animal animal = new Animal();
        Image image = (animalCreation.image() != null)
                ? imageMapper.multiPartFileToImage(animalCreation.image()) : null;
        animal.setName(animalCreation.name());
        animal.setSex(animalCreation.sex());
        animal.setSize(animalCreation.size());
        animal.setBirthDate(animalCreation.birthDate());
        animal.setWeight(animalCreation.weight());
        animal.setType(animalCreation.type());
        animal.setSterilization(animalCreation.sterilization());
        animal.setImage(image);
        return animal;
    }


}
