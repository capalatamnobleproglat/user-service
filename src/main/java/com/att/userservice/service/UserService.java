package com.att.userservice.service;

import com.att.userservice.dto.UserDto;
import org.springframework.data.crossstore.ChangeSetPersister;
import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Long id) throws ChangeSetPersister.NotFoundException;

    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long id, UserDto userDto) throws ChangeSetPersister.NotFoundException;

    void deleteUser(Long id) throws ChangeSetPersister.NotFoundException;
}
