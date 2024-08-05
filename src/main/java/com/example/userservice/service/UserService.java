package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.getUserById(userId);
    }

    public void deleteUserById(String userId) {
        userRepository.deleteUserById(userId);
    }
}
