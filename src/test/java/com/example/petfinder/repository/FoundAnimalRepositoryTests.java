package com.example.petfinder.repository;

import com.example.petfinder.model.entity.FoundAnimal;
import com.example.petfinder.model.entity.LostAnimal;
import com.example.petfinder.model.entity.User;
import com.example.petfinder.model.enums.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.petfinder.model.enums.Age.TWO;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FoundAnimalRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoundAnimalRepository foundAnimalRepository;
    private User user;


    @BeforeEach
    public void setUp() {
        user = User.builder()
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

        userRepository.save(user);
    }

    @Test
    public void FoundAnimalRepository_Save_ReturnSavedAnimal() {
        FoundAnimal animal = createFoundAnimal();

        FoundAnimal savedAnimal = foundAnimalRepository.save(animal);

        Assertions.assertThat(savedAnimal).isNotNull();
        Assertions.assertThat(savedAnimal.getId()).isNotNull();
    }

    @Test
    public void FoundAnimalRepository_FindAll_ReturnMoreThanOneAnimal() {
        FoundAnimal animal1 = createFoundAnimal();
        FoundAnimal animal2 = createFoundAnimal();

        foundAnimalRepository.save(animal1);
        foundAnimalRepository.save(animal2);

        List<FoundAnimal> savedAnimals = foundAnimalRepository.findAll();

        Assertions.assertThat(savedAnimals.size()).isEqualTo(2);
        Assertions.assertThat(savedAnimals).isNotNull();
    }

    @Test
    public void FoundAnimalRepository_FindById_ReturnAnimal() {
        FoundAnimal animal = createFoundAnimal();

        foundAnimalRepository.save(animal);

        FoundAnimal foundAnimal = foundAnimalRepository.findById(animal.getId()).get();

        Assertions.assertThat(foundAnimal).isNotNull();
    }

    @Test
    public void FoundAnimalRepository_UpdateAnimal_ReturnAnimalIsNotNull() {
        FoundAnimal animal = createFoundAnimal();

        foundAnimalRepository.save(animal);

        FoundAnimal savedAnimal = foundAnimalRepository.findById(animal.getId()).get();

        savedAnimal.setBreed("n");
        savedAnimal.setName("kara");

        FoundAnimal foundAnimal = foundAnimalRepository.save(savedAnimal);

        Assertions.assertThat(foundAnimal.getName()).isNotNull();
        Assertions.assertThat(foundAnimal.getBreed()).isNotNull();
    }

    @Test
    public void FoundAnimalRepository_DeleteById_ReturnAnimalIsEmpty() {
        FoundAnimal animal = createFoundAnimal();

        foundAnimalRepository.save(animal);
        foundAnimalRepository.deleteById(animal.getId());
        Optional<FoundAnimal> animalReturn = foundAnimalRepository.findById(animal.getId());

        Assertions.assertThat(animalReturn).isEmpty();
    }

    private FoundAnimal createFoundAnimal() {
        return FoundAnimal.builder()
                .name("Kyrylo")
                .about("small cat")
                .breed("fef")
                .sex(Sex.MALE)
                .size(Size.MIDDLE)
                .status(FoundAnimalStatus.FOUND)
                .distinguishingFeatures("gsr")
                .foundAt(LocalDate.of(1990, 5, 15))
                .type(Type.CAT)
                .user(user)
                .age(TWO)
                .build();
    }

}
