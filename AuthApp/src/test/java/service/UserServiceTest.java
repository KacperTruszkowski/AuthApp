package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.authapp.dto.UserDto;
import com.example.authapp.mapper.Mapper;
import com.example.authapp.model.User;
import com.example.authapp.repository.UserRepository;
import com.example.authapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRegisterUser() {
        UserDto userDto = new UserDto();
        userDto.setPassword("password");
        userDto.setLogin("login");
        User user = new User();
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(mapper.mapToUserDocument(userDto)).thenReturn(user);

        userService.registerUser(userDto);

        verify(userRepository).save(user);
    }

    @Test
    void shouldNotRegisterUserWithEmptyPassword() {
        UserDto userDto = new UserDto();
        userDto.setLogin("login");
        userDto.setPassword(""); // Puste hasło

        userService.registerUser(userDto);

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldLoginUser() {
        UserDto userDto = new UserDto();
        User user = new User();
        userDto.setLogin("test");
        userDto.setPassword("test");
        when(userRepository.findByLogin("test")).thenReturn(user);

        userService.loginUser(userDto);

        verify(userRepository).findByLogin("test");
    }

    @Test
    void shouldtAddItem() {
        String login = "username";
        String itemName = "item";
        User user = new User();
        when(userRepository.findByLogin(login)).thenReturn(user);
        userService.addItem(login, itemName);

        verify(userRepository).save(any());
    }

    @Test
    void shouldGetAllItems() {
        String login = "test";
        User user = new User();
        User.Item item = new User.Item();
        item.setId("123");
        item.setName("test");
        user.getItems().add(item);
        
        when(userRepository.findByLogin(login)).thenReturn(user);

        userService.getAllItems(login);
    }

    @Test
    void shouldMapToItemDto() {
        User.Item item = new User.Item();
        item.setId("123");
        item.setName("test");
        UserDto.ItemDto result = Mapper.mapToItemDto(item);
        assertEquals("123", result.getId());
        assertEquals("test", result.getItemName());
    }
}
