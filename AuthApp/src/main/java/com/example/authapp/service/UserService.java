package com.example.authapp.service;

import com.example.authapp.mapper.Mapper;
import com.example.authapp.dto.UserDto;
import com.example.authapp.exception.Exceptions;
import com.example.authapp.model.User;
import com.example.authapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

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

    public void loginUser (UserDto userDto) {
        userRepository.findByLogin(userDto.getLogin());
    }

    public void addItem(String login, String itemName) {
        userRepository.save(mapper.mapToItem(itemName, userRepository.findByLogin(login)));
    }

    public List<UserDto.ItemDto> getAllItems(String login) {
        User user = userRepository.findByLogin(login);
        return user.getItems().stream()
                .map(Mapper::mapToItemDto)
                .collect(Collectors.toList());
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
