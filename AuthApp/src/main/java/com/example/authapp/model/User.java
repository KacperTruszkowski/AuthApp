package com.example.authapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Document("users")
@Data
public class User {

    @Id
    private String id;
    @NotBlank(message = "Login cannot be null or empty")
    private String login;
    @NotBlank(message = "Password cannot be null or empty")
    private String password;
    private List<Item> items = new ArrayList<>();

    @Data
    public static class Item {

        @Id
        private String id;
        private String name;
        private String owner;
    }
}
