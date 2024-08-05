package com.example.userservice.repository;

import com.example.userservice.model.User;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final DynamoDbTable<User> userTable;

    @Autowired
    public UserRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.userTable = dynamoDbEnhancedClient.table("Users", TableSchema.fromBean(User.class));
    }

    public void saveUser(User user) {
        userTable.putItem(user);
    }

    public Optional<User> getUserByNickname(String nickname) {
        return Optional.ofNullable(userTable.getItem(r -> r.key(k -> k.partitionValue(nickname))));
    }

    public void deleteUserByNickname(String nickname) {
        userTable.deleteItem(r -> r.key(k -> k.partitionValue(nickname)));
    }

    public void updateUser(User user) {
        userTable.putItem(user);
    }

    public List<User> findAll() {
        Iterator<User> results = userTable.scan().items().iterator();
        List<User> users = new ArrayList<>();
        while (results.hasNext()) {
            users.add(results.next());
        }
        return users;
    }
}