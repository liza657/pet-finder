package com.example.petfinder.repository;

import com.example.petfinder.model.entity.Avatar;
import com.example.petfinder.model.entity.User;
import com.example.petfinder.model.enums.Role;
import com.example.petfinder.model.enums.Sex;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AvatarRepositoryTests {

    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void createUser() {
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
    }

    @Test
    public void AvatarRepository_Save_ReturnSavedAvatar() {
        Avatar avatar = createAvatar();

        avatarRepository.save(avatar);

        Assertions.assertThat(avatar).isNotNull();
    }

    @Test
    public void AnimalRepository_FindByName_ReturnAvatar() {
        Avatar avatar = createAvatar();

        userRepository.save(user);
        avatarRepository.save(avatar);

        Avatar foundAvatar = avatarRepository.findByName("s").get();

        Assertions.assertThat(foundAvatar.getName()).isEqualTo("s");

    }

    private Avatar createAvatar() {
        return Avatar.builder()
                .name("s")
                .user(user)
                .type("f")
                .imageData(getImageData())
                .build();
    }


    private byte[] getImageData() {
        return new byte[0];
    }
}
