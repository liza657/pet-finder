package com.example.petfinder.mapper;

import com.example.petfinder.dto.user.response.UserCard;
import com.example.petfinder.dto.user.response.UserView;
import com.example.petfinder.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.io.IOException;
import java.util.zip.DataFormatException;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {

    @Mapping(target = "avatar", expression = "java((user.getAvatar() != null) ? " +
            "com.example.petfinder.utils.ImageUtils.decompressImage(user.getAvatar().getImageData()) : null)")
    public abstract UserCard userToCard(User user) throws DataFormatException, IOException;

    public abstract UserView userToView(User user);
}
