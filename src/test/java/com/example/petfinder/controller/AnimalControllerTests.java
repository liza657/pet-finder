package com.example.petfinder.controller;

import com.example.petfinder.dto.animal.request.AnimalCreation;
import com.example.petfinder.dto.animal.respose.AnimalView;
import com.example.petfinder.model.entity.Animal;
import com.example.petfinder.model.entity.User;
import com.example.petfinder.model.enums.*;
import com.example.petfinder.service.AnimalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.example.petfinder.model.enums.Age.TWO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(controllers = AnimalController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AnimalControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    @Autowired
    private ObjectMapper objectMapper;
    private Animal animal;
    private User user;
    private AnimalView animalView;
    private AnimalCreation animalCreation;

    @BeforeEach
    public void init() {

        animal = Animal.builder().name("Kyrylo")
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

        animalCreation = AnimalCreation.builder()
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

        animalView = AnimalView.builder()
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
    public void AnimalController_CreateAnimal_ReturnCreated() throws Exception {
        given(animalService.addAnimal(any())).willAnswer((invocation -> invocation.getArgument(0)));
        MockMultipartFile image1 = new MockMultipartFile("image1", "image1.jpg", MediaType.IMAGE_JPEG_VALUE, "image1".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("image2", "image2.jpg", MediaType.IMAGE_JPEG_VALUE, "image2".getBytes());
        MockMultipartFile image3 = new MockMultipartFile("image3", "image3.jpg", MediaType.IMAGE_JPEG_VALUE, "image3".getBytes());
        MockMultipartFile image4 = new MockMultipartFile("image4", "image4.jpg", MediaType.IMAGE_JPEG_VALUE, "image4".getBytes());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.multipart("/animals")
                .file("animal", objectMapper.writeValueAsString(animal).getBytes())
                .file(image1)
                .file(image2)
                .file(image3)
                .file(image4));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Animal"));


    }
}
