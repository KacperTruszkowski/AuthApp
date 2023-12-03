package com.example.authapp.service;

import com.example.authapp.Mapper;
import com.example.authapp.dto.UserDto;
import com.example.authapp.exception.Exceptions;
import com.example.authapp.model.User;
import com.example.authapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Mapper mapper;

    public void registerUser(UserDto userDto) {
        validate(userDto);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(mapper.mapToUserDocument(userDto));
    }

    public String loginUser (UserDto userDto) {
        User user = userRepository.findByLogin(userDto.getLogin());
        return user.getLogin();
    }

    public void addItem(String login, String itemName) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            User.Item newItem = new User.Item();
            newItem.setName(itemName);
            newItem.setOwner(login);
            user.getItems().add(newItem);
            userRepository.save(user);
        }
    }

    public List<User> getAll () {
        return userRepository.findAll();
    }

    public List<User.Item> getAllItems(String login) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            return user.getItems();
        }
        return new ArrayList<>();
    }

    private void validate(UserDto userDto) {
        if (userDto.getLogin() == null || userDto.getPassword() == null) {
            throw new ValidationException("Login or password cannot be null");
        }
        if (userRepository.existsByLogin(userDto.getLogin())){
            throw new Exceptions.DuplicateLoginException("Login already exists");
        }
    }
}
