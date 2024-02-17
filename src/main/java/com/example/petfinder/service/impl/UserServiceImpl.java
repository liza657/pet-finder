package com.example.petfinder.service.impl;

import com.example.petfinder.dto.user.request.PasswordUpdateRequest;
import com.example.petfinder.dto.user.request.UserUpdateRequest;
import com.example.petfinder.dto.user.response.UserView;
import com.example.petfinder.exceptions.EntityIsAlreadyExists;
import com.example.petfinder.exceptions.EntityNotExistsException;
import com.example.petfinder.exceptions.InvalidPasswordException;
import com.example.petfinder.exceptions.PermissionException;
import com.example.petfinder.mapper.UserMapper;
import com.example.petfinder.model.entity.User;
import com.example.petfinder.repository.UserRepository;
import com.example.petfinder.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@Slf4j

public class UserServiceImpl implements UserService {
    private final static String ENTITY_IS_ALREADY_EXISTS = "User with this email is already exists";
    private final static String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EntityIsAlreadyExists(ENTITY_IS_ALREADY_EXISTS);
        }
        saveUser(user);
    }

    @Override
    public UserView getUserById(UUID userId) {
        User user = findUserById(userId);
        return userMapper.userToView(user);
    }


    @Override
    public UserView updateUser(UUID userId, UserUpdateRequest userUpdateRequest) {
        User user = getCurrentUser();

        checkPermission(userId);

        user.setFirstName(userUpdateRequest.firstName());
        user.setLastName(userUpdateRequest.lastName());
        user.setBiography(userUpdateRequest.biography());
        user.setSex(userUpdateRequest.sex());

        saveUser(user);
        return userMapper.userToView(user);
    }

    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        checkPermission(userId);
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        User user = getCurrentUser();
        if (!passwordEncoder.matches(passwordUpdateRequest.oldPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        user.setPassword(passwordEncoder.encode(passwordUpdateRequest.newPassword()));

    }


    public User getByUsername(String username) {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    private User findUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotExistsException(String.format(USER_NOT_FOUND, userId)));
    }

    private User findUserByUsername(String username) {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new EntityNotExistsException(String.format(USER_NOT_FOUND, username)));
    }

    private void checkPermission(UUID userId) {

        if (!getCurrentUser().getId().equals(userId)) {
            throw new PermissionException();
        }
    }

    private void saveUser(User user) {
        userRepository.save(user);
    }


}
