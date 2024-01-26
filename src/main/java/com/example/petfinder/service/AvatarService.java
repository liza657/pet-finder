package com.example.petfinder.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface AvatarService {

    void uploadAvatar(UUID userId,MultipartFile imageFile)throws IOException;

    byte[] downloadAvatar(String avatar);
}
