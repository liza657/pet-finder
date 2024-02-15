package com.example.petfinder.service;

import com.example.petfinder.dto.user.request.PasswordUpdateRequest;
import com.example.petfinder.dto.user.request.UserUpdateRequest;
import com.example.petfinder.dto.user.response.UserView;
import com.example.petfinder.exceptions.PermissionException;
import com.example.petfinder.mapper.UserMapper;
import com.example.petfinder.model.entity.User;
import com.example.petfinder.model.enums.Role;
import com.example.petfinder.model.enums.Sex;
import com.example.petfinder.repository.UserRepository;
import com.example.petfinder.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)

public class UserServiceTests {
    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;


    @Test
    public void UserService_CreateUser_Successful() {
        User user = createUser();

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        userService.createUser(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void UserService_GetUserById_ReturnUser() {
        User user = createUser();
        UserView userView = createUserView();


        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userService.getUserById(user.getId())).thenReturn(userView);

        UserView foundUser = userService.getUserById(user.getId());

        Assertions.assertThat(foundUser).isNotNull();
    }

    @Test
    public void UserService_GetUserByUsername_ReturnUser() {
        User user = createUser();
        String username = user.getUsername();

        when(userRepository.findUserByEmail(username)).thenReturn(Optional.of(user));

        User savedUser = userService.getByUsername(username);

        Assertions.assertThat(savedUser).isNotNull();
    }

    @Test
    public void UserService_DeleteUserById_ReturnUser() {
        User authenticatedUser = getUser();
        User user = createUser();

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        if (authenticatedUser.getEmail().equals(user.getEmail())) {
            userService.deleteUser(user.getId());

        } else throw new PermissionException("Permission Denied");


        verify(userRepository).deleteById(user.getId());
    }

    @Test
    public void UserService_UpdateUserById_ReturnUser() {
        User updatedUser = createUser();
        User currentUser = getUser();
        UserUpdateRequest userUpdateRequest = createUserUpdateRequest();
        UserView expectedUserView = createUserView();

        when(userRepository.findUserByEmail(currentUser.getEmail())).thenReturn(Optional.of(currentUser));
        when(userRepository.save(any(User.class))).thenReturn(currentUser);
        when(userMapper.userToView(any(User.class))).thenReturn(expectedUserView);

        if (!updatedUser.getUsername().equals(currentUser.getUsername())) {

            throw new PermissionException("Permission denied");
        }

        UserView result = userService.updateUser(currentUser.getId(), userUpdateRequest);

        assertNotNull(result);
        assertEquals(expectedUserView, result);

        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).userToView(any(User.class));
    }

    @Test
    public void UserService_UpdatePassword_ReturnSuccess() {
        User user = getUser();
        PasswordUpdateRequest passwordUpdateRequest = createPasswordUpdateRequest();

        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        userService.updatePassword(passwordUpdateRequest);

        verify(passwordEncoder, times(1)).encode(passwordUpdateRequest.newPassword());
    }

    private PasswordUpdateRequest createPasswordUpdateRequest() {
        return PasswordUpdateRequest.builder()
                .oldPassword("liza230)")
                .newPassword("liza23430))")
                .build();
    }

    private User createUser() {
        UUID userId = UUID.randomUUID();
        return User.builder()
                .id(userId)
                .email("elizabeth@gmail.com")
                .password("Liza))23")
                .firstName("Liza")
                .lastName("Doe")
                .biography("something")
                .phoneNumber("+123456789")
                .role(Role.USER)
                .sex(Sex.MALE)
                .birtDate(LocalDate.of(1990, 1, 1))
                .build();
    }

    private UserUpdateRequest createUserUpdateRequest() {
        return UserUpdateRequest.builder()
                .firstName("Liza")
                .lastName("Doe")
                .biography("something")
                .sex(Sex.MALE)
                .build();
    }

    private UserView createUserView() {
        return UserView.builder()
                .email("elizabeth@gmail.com")
                .firstName("Liza")
                .lastName("Doe")
                .phoneNumber("+123456789")
                .build();
    }


    private User getUser() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn("elizabeth@gmail.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        return createUser();
    }
}
