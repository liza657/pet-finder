package com.example.petfinder.service;

import com.example.petfinder.dto.user.request.PasswordUpdateRequest;
import com.example.petfinder.dto.user.request.UserUpdateRequest;
import com.example.petfinder.dto.user.response.UserView;
import com.example.petfinder.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService {

    void createUser(User user);

    User getByUsername(String username);

    UserDetailsService userDetailsService();

    UserView getUserById(UUID userId);

    void updateUser(UUID userId, UserUpdateRequest userUpdateRequest);

    void updatePassword(PasswordUpdateRequest passwordUpdateRequest, UserDetails userDetails);

    void deleteUser(UUID userId);
}
