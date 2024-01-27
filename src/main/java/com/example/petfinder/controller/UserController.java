package com.example.petfinder.controller;

import com.example.petfinder.dto.user.request.PasswordUpdateRequest;
import com.example.petfinder.dto.user.request.UserUpdateRequest;
import com.example.petfinder.dto.user.response.UserView;
import com.example.petfinder.service.AvatarService;
import com.example.petfinder.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;
    private final AvatarService avatarService;

    @GetMapping("get/{userId}")
    public ResponseEntity<UserView> getUser(@PathVariable("userId") UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
    }

    @DeleteMapping("delete/{userId}")
    public void deleteUser(@PathVariable("userId") UUID userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("password/update")
    public void updatePassword(@RequestBody PasswordUpdateRequest passwordUpdateRequest, @AuthenticationPrincipal UserDetails userDetails) {
        userService.updatePassword(passwordUpdateRequest, userDetails);
    }

    @PutMapping("update/{userId}")
    public void updateUser(@PathVariable("userId") UUID userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userId, userUpdateRequest);
    }

    @PostMapping("avatar/{userId}/upload")
    public void uploadAvatar(@PathVariable("userId") UUID userId, @RequestParam("avatar") MultipartFile file) throws IOException {
        log.info(userId.toString());
        avatarService.uploadAvatar(userId, file);
    }

    @GetMapping("avatar/download/{avatarId}")
    public ResponseEntity<?> downloadAvatar(@PathVariable UUID avatarId) {
        byte[] avatarData = avatarService.downloadAvatar(avatarId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
                .body(avatarData);
    }

}
