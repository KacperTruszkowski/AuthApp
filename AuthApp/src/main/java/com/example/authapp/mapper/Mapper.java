package com.example.authapp.mapper;

import com.example.authapp.dto.UserDto;
import com.example.authapp.model.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Mapper {

    public User mapToUserDocument(UserDto userDto) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public User mapToItem(String itemName, User user) {
        User.Item newItem = new User.Item();
        newItem.setId(UUID.randomUUID().toString());
        newItem.setName(itemName);
        newItem.setOwner(user.getId());
        user.getItems().add(newItem);
        return user;
    }

    public static UserDto.ItemDto mapToItemDto(User.Item item) {
        UserDto.ItemDto itemDto = new UserDto.ItemDto();
        itemDto.setId(item.getId());
        itemDto.setItemName(item.getName());
        itemDto.setOwner(item.getOwner());
        return itemDto;
    }
}
