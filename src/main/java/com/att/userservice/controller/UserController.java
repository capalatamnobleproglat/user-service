package com.att.userservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.att.userservice.dto.UserDto;
import com.att.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService UserService;

    @Autowired
    public UserController(UserService UserService) {
        this.UserService = UserService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return UserService.getAllUsers();
    }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto UserDto) {
        return UserService.createUser(UserDto);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return UserService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto UserDto) throws ChangeSetPersister.NotFoundException {
        return UserService.updateUser(id, UserDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        UserService.deleteUser(id);
    }
}
