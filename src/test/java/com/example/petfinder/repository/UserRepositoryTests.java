package com.example.petfinder.repository;

import com.example.petfinder.model.entity.User;
import com.example.petfinder.model.enums.Role;
import com.example.petfinder.model.enums.Sex;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_FindUserByEmail_ReturnUser() {
        User user = createUser();

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findUserByEmail(user.getEmail());

        Assertions.assertThat(foundUser).isNotEmpty();
    }

    @Test
    public void UserRepository_ExistsByEmail_ReturnUser() {
        User user = createUser();

        userRepository.save(user);

        boolean foundUser = userRepository.existsByEmail(user.getEmail());

        Assertions.assertThat(foundUser).isTrue();
    }

    @Test
    public void UserRepository_SaveUser_UserIsNotNull() {
        User user = createUser();

        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
    }

    @Test
    public void UserRepository_FindUserById_ReturnUser() {
        User user = createUser();

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(user.getId());

        Assertions.assertThat(foundUser).isNotNull();
    }

    @Test
    public void UserRepository_UpdateUser_ReturnUser() {
        User user = createUser();

        userRepository.save(user);

        User savedUser = userRepository.findById(user.getId()).get();

        savedUser.setFirstName("Liza");
        savedUser.setBiography("student");
        userRepository.save(savedUser);

        Assertions.assertThat(savedUser.getFirstName()).isNotNull();
        Assertions.assertThat(savedUser.getBiography()).isNotNull();
    }

    @Test
    public void UserRepository_DeleteById_ReturnUserIsNull() {
        User user = createUser();

        userRepository.save(user);
        userRepository.deleteById(user.getId());

        Optional<User> deletedUser=userRepository.findById(user.getId());

        Assertions.assertThat(deletedUser).isEmpty();
    }

    private User createUser() {
        return User.builder()
                .email("elizabeth@gmail.com")
                .password("Liza)30")
                .firstName("l")
                .lastName("l")
                .biography("faefe")
                .phoneNumber("0951110428")
                .role(Role.USER)
                .sex(Sex.FEMALE)
                .birtDate(LocalDate.of(1990, 5, 15))
                .build();
    }

}
