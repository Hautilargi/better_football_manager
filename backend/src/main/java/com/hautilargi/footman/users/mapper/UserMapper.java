package com.hautilargi.footman.users.mapper;

import org.mapstruct.Mapper;

import com.hautilargi.footman.users.dto.UserDto;
import com.hautilargi.footman.users.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);
}
