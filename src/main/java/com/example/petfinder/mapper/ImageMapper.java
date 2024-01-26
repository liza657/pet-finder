package com.example.petfinder.mapper;

import com.example.petfinder.model.entity.Image;
import com.example.petfinder.utils.ImageUtils;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ImageMapper {

    public Image multiPartFileToImage(MultipartFile multipartFile) throws IOException {
        return new Image(multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                ImageUtils.compressImage(multipartFile.getBytes()));
    }
}
