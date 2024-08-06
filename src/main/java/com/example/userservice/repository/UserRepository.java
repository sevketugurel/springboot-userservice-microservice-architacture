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

    // User nesnelerini tutacak DynamoDB tablosunu temsil eder.
    private final DynamoDbTable<User> userTable;

    //Bu metod enhancedclient kullanarak “Users” adındaki DynamoDB tablosunu ve User sınıfına karşılık gelen tablo
    // şemasını oluşturur.
    @Autowired
    public UserRepository(DynamoDbEnhancedClient DynamoDbIstemcisi) {
        // User sınıfına karşılık gelen tablo şemasını oluşturuluyor
        this.userTable = DynamoDbIstemcisi.table("Users", TableSchema.fromBean(User.class));
    }

    public void saveUser(User user) {
        userTable.putItem(user); //Verilen kullanıcı nesnesini tabloya eklniyor
    }

    // nickname alanına göre kullanıcı arar ve bulursa Optional<User> nesnesi döner.
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
        // Tablodaki tüm kullanıcıları döner ve bir liste oluşturur
        Iterator<User> results = userTable.scan().items().iterator();
        List<User> users = new ArrayList<>();
        // Tüm sonuçları listeye ekler
        while (results.hasNext()) {
            users.add(results.next());
        }
        return users;
    }
}