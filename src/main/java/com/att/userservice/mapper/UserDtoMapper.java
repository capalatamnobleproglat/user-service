package com.att.userservice.mapper;

import org.mapstruct.Mapper;

import com.att.userservice.dto.UserDto;
import com.att.userservice.model.User;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    User UserDTO2User(UserDto userDTO);

}