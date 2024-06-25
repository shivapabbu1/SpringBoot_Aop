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
    
    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable Long id) {
    	UserEntity user = userService.getUserById(id);
       
        	 if (user != null) {
                
                 return "User with given id: " + id + ": " +user;
             } else {
                 return "User with id " + id + " not found";
             }
    }

    @GetMapping("/all")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }
}
