package com.example.petfinder.service;

import com.example.petfinder.dto.animal.request.AnimalCreation;
import com.example.petfinder.dto.animal.respose.AnimalCard;
import com.example.petfinder.dto.animal.respose.AnimalView;
import com.example.petfinder.mapper.AnimalMapper;
import com.example.petfinder.model.entity.Animal;
import com.example.petfinder.model.entity.User;
import com.example.petfinder.model.enums.*;
import com.example.petfinder.repository.AnimalRepository;
import com.example.petfinder.repository.UserRepository;
import com.example.petfinder.service.impl.AnimalServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

import static com.example.petfinder.model.enums.Age.TWO;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class AnimalServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AnimalMapper animalMapper;

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalServiceImpl animalService;
    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;


    private User user;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }


    public User createUser() {
        return User.builder()
                .firstName("Liza")
                .lastName("Doe")
                .sex(Sex.MALE)
                .biography("something")
                .email("elizabeth@gmail.com")
                .birtDate(LocalDate.of(1990, 1, 1))
                .password("Liza))23")
                .phoneNumber("+123456789")
                .role(Role.USER)
                .build();
    }

    public AnimalCreation createAnimalCreation() {
        return AnimalCreation.builder()
                .name("Fluffy")
                .age(Age.TWO)
                .story("A lovely pet")
                .breed("Persian")
                .traits("Friendly")
                .healthHistory("Good")
                .weight(BigDecimal.valueOf(10.5))
                .sterilization(Sterilization.TRUE)
                .sex(Sex.FEMALE)
                .size(Size.SMALL)
                .type(Type.CAT)
                .build();
    }

    @Test
    public void testGetCurrentUser() {

        User expectedUser = createUser();
        String expectedUsername = "elizabeth@gmail.com";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(expectedUsername);
        when(userRepository.findUserByEmail(expectedUsername)).thenReturn(Optional.of(expectedUser));

        User currentUser = animalService.getCurrentUser();

        assertEquals(expectedUser, currentUser);
    }

    @Test
    public void AnimalService_CreateAnimal_ReturnAnimalView() throws DataFormatException, IOException {
        getAuthenticatedUser();
        AnimalCreation animalCreation = createAnimalCreation();
        Animal animal = new Animal();
        AnimalView expectedAnimalView = mock(AnimalView.class);

        when(animalMapper.newToAnimal(animalCreation)).thenReturn(animal);
        when(animalRepository.save(animal)).thenReturn(animal);
        when(animalMapper.animalToView(animal)).thenReturn(expectedAnimalView);

        AnimalView actualAnimalView = animalService.addAnimal(animalCreation);
        Assertions.assertThat(actualAnimalView).isNotNull();

    }

    @Test
    public void AnimalService_GetAnimalById_ReturnAnimal() throws DataFormatException, IOException {
        Animal animal = createAnimal();
        AnimalView animalView = createAnimalView();

        when(animalRepository.findById(animal.getId())).thenReturn(Optional.of(animal));
        when(animalService.getAnimalById(animal.getId())).thenReturn(animalView);

        AnimalView savedAnimal = animalService.getAnimalById(animal.getId());

        Assertions.assertThat(savedAnimal).isNotNull();
    }

    @Test
    public void AnimalService_GetAllAnimals_ReturnMoreThanOne() throws DataFormatException, IOException {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal());
        animals.add(new Animal());
        Page<Animal> animalPage = new PageImpl<>(animals);

        List<AnimalCard> animalCards = new ArrayList<>();
        animalCards.add(createAnimalCard());
        animalCards.add(createAnimalCard());
        Page<AnimalCard> expectedAnimalCardPage = new PageImpl<>(animalCards);

        when(animalRepository.findAll(any(Pageable.class))).thenReturn(animalPage);
        when(animalMapper.animalToCard(any())).thenReturn(createAnimalCard()); // Assuming your mapper returns a card for any animal

        Page<AnimalCard> actualAnimalCardPage = animalService.getAllAnimals(Pageable.unpaged());

        assertEquals(expectedAnimalCardPage.getContent().size(), actualAnimalCardPage.getContent().size());

    }

    @Test
    public void AnimalService_DeleteById_ReturnAnimalIsNull() {
        getAuthenticatedUser();

        Animal animal = createAnimal();
        when(animalRepository.findById(animal.getId())).thenReturn(Optional.of(animal));

        // Act
        animalService.deleteAnimal(animal.getId());

        // Assert
        verify(animalRepository).deleteById(animal.getId());
    }

    private void getAuthenticatedUser() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn("test@example.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        User mockUser = new User();
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(mockUser));
    }


    private Animal createAnimal() {
        return Animal.builder()
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
    }

    private AnimalView createAnimalView() {
        return AnimalView.builder()
                .name("Kyrylo")
                .story("small cat")
                .breed("fef")
                .sex(Sex.MALE)
                .size(Size.MIDDLE)
                .sterilization(Sterilization.FALSE)
                .type(Type.CAT)
                .weight(BigDecimal.ONE)
                .age(TWO).build();
    }

    private AnimalCard createAnimalCard() {
        return AnimalCard.builder()
                .name("Kyrylo")
                .sex(Sex.MALE)
                .sterilization(Sterilization.FALSE)
                .type(Type.CAT)
                .weight(BigDecimal.ONE)
                .age(TWO)
                .build();
    }


}
