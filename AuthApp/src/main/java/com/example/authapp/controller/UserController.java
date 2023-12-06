package com.example.authapp.controller;

import com.example.authapp.dto.UserDto;
import com.example.authapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated UserDto userDto) {
        userService.registerUser(userDto);
        return ResponseEntity.status(HttpStatus.OK).body("Registering successful");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto user) {
        userService.loginUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("Login successful");
    }

    @PostMapping("/addItem")
    public ResponseEntity<String> addItem(@RequestBody UserDto.ItemDto item, Authentication authentication) {
        String login = authentication.getName();
        userService.addItem(login, item.getItemName());
        return ResponseEntity.ok("Add item successful");
    }

    @GetMapping("/getItems")
    public List<UserDto.ItemDto> getAllItems(Authentication authentication) {
        String login = authentication.getName();
        return userService.getAllItems(login);
    }
}
