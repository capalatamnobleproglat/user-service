package com.att.userservice.service;

import com.att.userservice.dto.UserDto;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Long id) throws ChangeSetPersister.NotFoundException;

    UserDto createUser(UserDto UserDto);

    UserDto updateUser(Long id, UserDto UserDto) throws ChangeSetPersister.NotFoundException;

    void deleteUser(Long id) throws ChangeSetPersister.NotFoundException;
}
