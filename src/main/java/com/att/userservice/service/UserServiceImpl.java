package com.att.userservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import com.att.userservice.model.User;
import com.att.userservice.dto.UserDto;
import com.att.userservice.mapper.UserDtoMapper;
import com.att.userservice.mapper.UserMapper;
import com.att.userservice.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository UserRepository;

    private final UserMapper userMapper;

    private final UserDtoMapper userDtoMapper;
    
    public UserServiceImpl(UserRepository UserRepository, UserMapper userMapper, UserDtoMapper userDtoMapper) {
        this.UserRepository = UserRepository;
        this.userDtoMapper = userDtoMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> Users = UserRepository.findAll();
        return Users.stream()
                .map(userMapper::User2UserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto UserDto) {
        User User = userDtoMapper.UserDTO2User(UserDto);
        User = UserRepository.save(User);
        return userMapper.User2UserDTO(User);
    }

    @Override
    public UserDto getUserById(Long id) throws ChangeSetPersister.NotFoundException {
        User User = UserRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return userMapper.User2UserDTO(User);
    }

    @Override
    public UserDto updateUser(Long id, UserDto UserDto) throws ChangeSetPersister.NotFoundException {
        User existingUser = UserRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        BeanUtils.copyProperties(UserDto, existingUser, "id");

        existingUser = UserRepository.save(existingUser);
        return userMapper.User2UserDTO(existingUser);
    }

    @Override
    public void deleteUser(Long id) throws ChangeSetPersister.NotFoundException {
        User User = UserRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        UserRepository.delete(User);
    }

}
