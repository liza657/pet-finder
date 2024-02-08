package com.example.petfinder.repository;

import com.example.petfinder.model.entity.Animal;
import com.example.petfinder.model.entity.User;
import com.example.petfinder.model.enums.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.petfinder.model.enums.Age.TWO;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AnimalRepositoryTests {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void AnimalRepository_Save_ReturnSavedAnimal() {

        Animal animal = Animal.builder()
                .name("Kyrylo")
                .story("small cat")
                .breed("fef")
                .age(TWO).build();

        Animal savedAnimal = animalRepository.save(animal);

        Assertions.assertThat(savedAnimal).isNotNull();
        Assertions.assertThat(savedAnimal.getId()).isNotNull();

    }

    @Test
    public void AnimalRepository_FindAll_ReturnMoreThanOneAnimal() {
        User user = User.builder()
                .firstName("sfes")
                .lastName("fse")
                .sex(Sex.MALE)
                .biography("esfe")
                .email("john.doe@example.com")
                .birtDate(LocalDate.of(1990, 5, 15))
                .password("fawerfwfeA234")
                .phoneNumber("fgergfe")
                .role(Role.USER)
                .build();


        Animal animal1 = Animal.builder()
                .name("Kyrylo")
                .story("small cat")
                .breed("fef")
                .sex(Sex.MALE)
                .size(Size.MIDDLE)
                .status(Status.ADOPTED)
                .sterilization(Sterilization.FALSE)
                .type(Type.CAT)
                .weight(BigDecimal.ONE)
                .owner(user)
                .age(TWO).build();

        Animal animal2 = Animal.builder()
                .name("Kyrylo")
                .story("small cat")
                .breed("fef")
                .status(Status.ADOPTED)
                .sterilization(Sterilization.FALSE)
                .type(Type.CAT)
                .weight(BigDecimal.ONE)
                .sex(Sex.MALE)
                .size(Size.MIDDLE)
                .owner(user)
                .age(TWO).build();

        userRepository.save(user);
        animalRepository.save(animal1);
        animalRepository.save(animal2);

        List<Animal> savedAnimals = animalRepository.findAll();

        Assertions.assertThat(savedAnimals).isNotNull();
        Assertions.assertThat(savedAnimals.size()).isEqualTo(2);

    }

    @Test
    public void AnimalRepository_FindById_ReturnAnimal() {
        User user = User.builder()
                .firstName("sfes")
                .lastName("fse")
                .sex(Sex.MALE)
                .biography("esfe")
                .email("john.doe@example.com")
                .birtDate(LocalDate.of(1990, 5, 15))
                .password("fawerfwfeA234")
                .phoneNumber("fgergfe")
                .role(Role.USER)
                .build();


        Animal animal1 = Animal.builder()
                .name("Kyrylo")
                .story("small cat")
                .breed("fef")
                .sex(Sex.MALE)
                .size(Size.MIDDLE)
                .status(Status.ADOPTED)
                .sterilization(Sterilization.FALSE)
                .type(Type.CAT)
                .weight(BigDecimal.ONE)
                .owner(user)
                .age(TWO).build();

        userRepository.save(user);
        animalRepository.save(animal1);

        Animal animal = animalRepository.findById(animal1.getId()).get();

        Assertions.assertThat(animal).isNotNull();

    }


    @Test
    public void AnimalRepository_UpdateAnimal_ReturnAnimalNotNull() {
        User user = User.builder()
                .firstName("sfes")
                .lastName("fse")
                .sex(Sex.MALE)
                .biography("esfe")
                .email("john.doe@example.com")
                .birtDate(LocalDate.of(1990, 5, 15))
                .password("fawerfwfeA234")
                .phoneNumber("fgergfe")
                .role(Role.USER)
                .build();


        Animal animal1 = Animal.builder()
                .name("Kyrylo")
                .story("small cat")
                .breed("fef")
                .sex(Sex.MALE)
                .size(Size.MIDDLE)
                .status(Status.ADOPTED)
                .sterilization(Sterilization.FALSE)
                .type(Type.CAT)
                .weight(BigDecimal.ONE)
                .owner(user)
                .age(TWO).build();

        userRepository.save(user);
        animalRepository.save(animal1);

        Animal savedAnimal = animalRepository.findById(animal1.getId()).get();

        savedAnimal.setBreed("n");
        savedAnimal.setName("kara");

        Animal updatedAnimal = animalRepository.save(savedAnimal);
        Assertions.assertThat(updatedAnimal.getName()).isNotNull();
        Assertions.assertThat(updatedAnimal.getBreed()).isNotNull();
    }

    @Test
    public void AnimalRepository_DeleteById_ReturnAnimalIsEmpty() {

        User user = User.builder()
                .firstName("sfes")
                .lastName("fse")
                .sex(Sex.MALE)
                .biography("esfe")
                .email("john.doe@example.com")
                .birtDate(LocalDate.of(1990, 5, 15))
                .password("fawerfwfeA234")
                .phoneNumber("fgergfe")
                .role(Role.USER)
                .build();


        Animal animal1 = Animal.builder()
                .name("Kyrylo")
                .story("small cat")
                .breed("fef")
                .sex(Sex.MALE)
                .size(Size.MIDDLE)
                .status(Status.ADOPTED)
                .sterilization(Sterilization.FALSE)
                .type(Type.CAT)
                .weight(BigDecimal.ONE)
                .owner(user)
                .age(TWO).build();

        userRepository.save(user);
        animalRepository.save(animal1);

        animalRepository.deleteById(animal1.getId());
        Optional<Animal> animalReturn = animalRepository.findById(animal1.getId());

        Assertions.assertThat(animalReturn).isEmpty();

    }


}
