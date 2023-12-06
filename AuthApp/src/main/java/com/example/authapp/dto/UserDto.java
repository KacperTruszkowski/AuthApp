package com.example.authapp.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserDto {

    @Id
    private String id;
    @NotNull(message = "Login cannot be null")
    private String login;
    @NotNull(message = "Password cannot be null or empty")
    private String password;
    private List<ItemDto> items;

    @Data
    public static class ItemDto {
        @Id
        private String id;
        private String itemName;
        private String owner;
    }

}
