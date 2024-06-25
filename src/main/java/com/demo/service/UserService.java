package com.demo.service;

import com.demo.entity.UserEntity;
import com.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity saveUser(UserEntity user) {
        try {
           
            if (user.getName() != null && userRepository.existsByName(user.getName())) {
                throw new RuntimeException("User with name " + user.getName() + " already exists");
            }
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

    public UserEntity getUserById(Long id) {
        try {
            return userRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user by id: " + id, e);
        }
    }

    public List<UserEntity> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all users", e);
        }
    }
}
