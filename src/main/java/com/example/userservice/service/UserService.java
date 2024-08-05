package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        // Ensure nickname is unique
        if (userRepository.getUserByNickname(user.getNickname()).isPresent()) {
            throw new IllegalArgumentException("Nickname already exists");
        }
        // Generate userId
        user.setUserId(UUID.randomUUID().toString());
        userRepository.saveUser(user);
    }

    public Optional<User> getUserByNickname(String nickname) {
        return userRepository.getUserByNickname(nickname);
    }

    public void deleteUserByNickname(String nickname) {
        userRepository.deleteUserByNickname(nickname);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}