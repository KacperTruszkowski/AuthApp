package com.example.authapp;

import com.example.authapp.dto.UserDto;
import com.example.authapp.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public User mapToUserDocument(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
