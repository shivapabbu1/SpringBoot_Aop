package com.demo.controller;

import com.demo.entity.UserEntity;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public UserEntity saveUser(@RequestBody UserEntity user) {
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
    	return userService.getUserById(id);
       
    }

    @GetMapping("/all")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }
}
