package com.example.authapp.controller;

import com.example.authapp.dto.UserDto;
import com.example.authapp.model.User;
import com.example.authapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated UserDto userDto, @AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
        userService.registerUser(userDto);
        return ResponseEntity.status(HttpStatus.OK).body("Registering successful");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto user) {
        userService.loginUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("Login successful");
    }

    @GetMapping("/users")
    public List<User> users() {
        return userService.getAll();
    }

    @PostMapping("/addItem")
    public ResponseEntity<String> addItem(@RequestBody UserDto.Item item, @RequestParam String login) {
        userService.addItem(login, item.getItemName());
        return ResponseEntity.ok("Add item successful");
    }

    @GetMapping("/items")
    public List<User.Item> getAllItems(@RequestParam String login) {
        return userService.getAllItems(login);
    }
}
