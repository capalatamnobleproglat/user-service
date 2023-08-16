package com.att.userservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import com.att.userservice.model.User;
import com.att.userservice.dto.UserDto;
import com.att.userservice.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        user = userRepository.save(user);
        return convertToDto(user);
    }

    @Override
    public UserDto getUserById(Long id) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return convertToDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) throws ChangeSetPersister.NotFoundException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Actualizar los campos del usuario existente con la información del DTO
        BeanUtils.copyProperties(userDto, existingUser, "id");

        existingUser = userRepository.save(existingUser);
        return convertToDto(existingUser);
    }

    @Override
    public void deleteUser(Long id) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        userRepository.delete(user);
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }
}
