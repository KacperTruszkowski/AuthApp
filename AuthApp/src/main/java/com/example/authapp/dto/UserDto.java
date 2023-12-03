package com.example.authapp.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class UserDto {

    @Id
    private String id;
    @NotBlank(message = "Login cannot be null")
    private String login;
    @NotBlank(message = "Password cannot be null or empty")
    private String password;
    private List<Item> items;

    @Data
    public static class Item {
        @Id
        private String id;
        private String itemName;
        private String owner;
    }

}
