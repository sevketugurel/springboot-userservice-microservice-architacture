package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.LinkedHashMap;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String ORDER_SERVICE_URL = "http://localhost:8081/orders";

    public void saveUser(User user) {
        if (userRepository.getUserByNickname(user.getNickname()).isPresent()) {
            throw new IllegalArgumentException("Nickname daha önce kullanılmış");
        }
        user.setUserId(UUID.randomUUID().toString());
        userRepository.saveUser(user);
    }

    public Optional<User> getUserByNickname(String nickname) {
        return userRepository.getUserByNickname(nickname);
    }

    public void deleteUserByNickname(String nickname) {
        // Kullanıcıya ait siparişleri de sil
        String url = ORDER_SERVICE_URL + "/" + nickname;
        ResponseEntity<Object[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Object[].class
        );
        Object[] orders = response.getBody();
        if (orders != null) {
            for (Object order : orders) {
                restTemplate.delete(ORDER_SERVICE_URL + "/" + nickname + "/" + ((LinkedHashMap) order).get("orderId").toString());
            }
        }
        userRepository.deleteUserByNickname(nickname);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Object> getUserOrders(String nickname) {
        String url = ORDER_SERVICE_URL + "/" + nickname;
        ResponseEntity<Object[]> response = restTemplate.getForEntity(url, Object[].class);
        return List.of(response.getBody());
    }

    public void createUserOrder(String nickname, Object order) {
        ((LinkedHashMap) order).put("nickname", nickname);
        restTemplate.postForEntity(ORDER_SERVICE_URL, order, String.class);
    }
}