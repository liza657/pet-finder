package com.example.petfinder.mapper;

import com.example.petfinder.dto.user.response.UserCard;
import com.example.petfinder.dto.user.response.UserView;
import com.example.petfinder.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {


    public abstract UserCard userToCard(User user);

    public abstract UserView userToView(User user);
}
