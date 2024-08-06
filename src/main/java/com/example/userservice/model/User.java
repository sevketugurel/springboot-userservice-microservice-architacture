package com.example.userservice.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

// DynanmıDB tablosu için User sınıfı
@DynamoDbBean
public class User {
    private String userId;
    private String nickname;
    private String name;
    private String email;

    // getter ve setter metotları
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // nickname alanının DynamoDB tablosunda birincil anahtar olarak kullanılacağını belirtir.
    @DynamoDbPartitionKey
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}