package com.example.petfinder.repository;

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
public class LostAnimalRepositoryTests {

    @Autowired
    private LostAnimalRepository lostAnimalRepository;

    @Autowired
    private UserRepository userRepository;
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
    public void LostAnimalRepository_Save_ReturnSavedAnimal() {
        LostAnimal animal = createLostAnimal();

        LostAnimal savedAnimal = lostAnimalRepository.save(animal);

        Assertions.assertThat(savedAnimal).isNotNull();
        Assertions.assertThat(savedAnimal.getId()).isNotNull();
    }

    @Test
    public void LostAnimalRepository_FindAll_ReturnMoreThanOneAnimal() {
        LostAnimal animal1 = createLostAnimal();
        LostAnimal animal2 = createLostAnimal();

        lostAnimalRepository.save(animal1);
        lostAnimalRepository.save(animal2);

        List<LostAnimal> savedAnimals = lostAnimalRepository.findAll();

        Assertions.assertThat(savedAnimals.size()).isEqualTo(2);
        Assertions.assertThat(savedAnimals).isNotNull();
    }

    @Test
    public void LostAnimalRepository_FindById_ReturnAnimal() {
        LostAnimal animal = createLostAnimal();

        lostAnimalRepository.save(animal);

        LostAnimal lostAnimal = lostAnimalRepository.findById(animal.getId()).get();

        Assertions.assertThat(lostAnimal).isNotNull();
    }

    @Test
    public void LostAnimalRepository_UpdateAnimal_ReturnAnimalIsNotNull() {
        LostAnimal animal = createLostAnimal();

        lostAnimalRepository.save(animal);

        LostAnimal savedAnimal = lostAnimalRepository.findById(animal.getId()).get();

        savedAnimal.setBreed("n");
        savedAnimal.setName("kara");

        LostAnimal lostAnimal = lostAnimalRepository.save(savedAnimal);

        Assertions.assertThat(lostAnimal.getName()).isNotNull();
        Assertions.assertThat(lostAnimal.getBreed()).isNotNull();
    }

    @Test
    public void LostAnimalRepository_DeleteById_ReturnAnimalIsEmpty() {
        LostAnimal animal = createLostAnimal();

        lostAnimalRepository.save(animal);
        lostAnimalRepository.deleteById(animal.getId());
        Optional<LostAnimal> animalReturn = lostAnimalRepository.findById(animal.getId());

        Assertions.assertThat(animalReturn).isEmpty();
    }

    private LostAnimal createLostAnimal() {
        return LostAnimal.builder()
                .name("Kyrylo")
                .about("small cat")
                .breed("fef")
                .sex(Sex.MALE)
                .size(Size.MIDDLE)
                .status(LostAnimalStatus.FOUND)
                .distinguishingFeatures("gsr")
                .foundAt(LocalDate.of(1990, 5, 15))
                .type(Type.CAT)
                .user(user)
                .age(TWO)
                .build();
    }

}
