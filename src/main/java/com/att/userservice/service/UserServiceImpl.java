package com.att.userservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import com.att.userservice.repository.UserRepository;
import java.util.List;
import com.att.userservice.dto.UserDto;
import com.att.userservice.model.User;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository UserRepository;

    public UserServiceImpl(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> Users = UserRepository.findAll();
        return Users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto UserDto) {
        User User = convertToEntity(UserDto);
        User = UserRepository.save(User);
        return convertToDto(User);
    }

    @Override
    public UserDto getUserById(Long id) throws ChangeSetPersister.NotFoundException {
        User User = UserRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return convertToDto(User);
    }

    @Override
    public UserDto updateUser(Long id, UserDto UserDto) throws ChangeSetPersister.NotFoundException {
        User existingUser = UserRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Actualizar los campos del Usero existente con la información del DTO
        BeanUtils.copyProperties(UserDto, existingUser, "id");

        existingUser = UserRepository.save(existingUser);
        return convertToDto(existingUser);
    }

    @Override
    public void deleteUser(Long id) throws ChangeSetPersister.NotFoundException {
        User User = UserRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        UserRepository.delete(User);
    }

    private UserDto convertToDto(User User) {
        UserDto UserDto = new UserDto();
        BeanUtils.copyProperties(User, UserDto);
        return UserDto;
    }

    private User convertToEntity(UserDto UserDto) {
        User User = new User();
        BeanUtils.copyProperties(UserDto, User);
        return User;
    }
}
