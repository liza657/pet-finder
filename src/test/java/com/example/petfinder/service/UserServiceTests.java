package com.example.petfinder.service;

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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)

public class UserServiceTests {
    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;


    @InjectMocks
    UserServiceImpl userService;


    @Test
    public void testCreateUser_Successful() {
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

        UserView savedUser = userService.getUserById(user.getId());

        Assertions.assertThat(savedUser).isNotNull();
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
        setUpSecurityContext();
        User user = createUser();
        userRepository.save(user);
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        userService.deleteUser(user.getId());

        verify(userRepository).deleteById(user.getId());
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

    private UserView createUserView() {
        return UserView.builder()
                .email("elizabeth@gmail.com")
                .firstName("Liza")
                .lastName("Doe")
                .phoneNumber("+123456789")
                .build();
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    public User getByUsername(String username) {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

    private void checkPermission(UUID userId) {

        if (!getCurrentUser().getId().equals(userId)) {
            throw new PermissionException();
        }
    }

    private void setCurrentUser(User user) {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getEmail());
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    private void setUpSecurityContext() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn("testUser");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

}
