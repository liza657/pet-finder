package com.example.petfinder.service.impl;

import com.example.petfinder.exceptions.EntityNotExistsException;
import com.example.petfinder.exceptions.PermissionException;
import com.example.petfinder.model.entity.Avatar;
import com.example.petfinder.model.entity.User;
import com.example.petfinder.repository.AvatarRepository;
import com.example.petfinder.repository.UserRepository;
import com.example.petfinder.service.AvatarService;
import com.example.petfinder.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvatarServiceImpl implements AvatarService {

    private final ImageUtils imageUtils;
    private final AvatarRepository avatarRepository;

    private final UserRepository userRepository;

    private final static String USER_NOT_FOUND = "User not found";


    @Override
    public void uploadAvatar(UUID userId, MultipartFile avatar) throws IOException {
        User user = findUserById(userId);
        checkPermission(userId);
        var avatarToSave = Avatar.builder()
                .name(avatar.getOriginalFilename())
                .type(avatar.getContentType())
                .imageData(ImageUtils.compressImage(avatar.getBytes()))
                .build();
        user.setAvatar(avatarToSave);
        userRepository.save(user);
        avatarRepository.save(avatarToSave);
    }

    @Override
    public byte[] downloadAvatar(String avatar) {
        Optional<Avatar> dbAvatar = avatarRepository.findByName(avatar);
        return dbAvatar.map(a -> {
            try {
                return ImageUtils.decompressImage(a.getImageData());
            } catch (DataFormatException | IOException exception) {
                throw new ContextedRuntimeException("Error downloading an image", exception)
                        .addContextValue("Image ID", a.getId())
                        .addContextValue("Image name", a);
            }
        }).orElse(null);
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

    private User findUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotExistsException(String.format(USER_NOT_FOUND, userId)));
    }

}
