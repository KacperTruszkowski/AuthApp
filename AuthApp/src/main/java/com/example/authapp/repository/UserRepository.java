package com.example.authapp.repository;

import com.example.authapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository <User, String> {

    User findByLogin(String login);

    boolean existsByLogin(String login);
}
