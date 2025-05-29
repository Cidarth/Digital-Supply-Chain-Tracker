package Digital.Supply.tracker.service;

import Digital.Supply.tracker.dto.UserDto;
import Digital.Supply.tracker.entity.User;
import Digital.Supply.tracker.exception.EmailAlreadyExistsException;
import Digital.Supply.tracker.repository.UserRepository;
import Digital.Supply.tracker.regexValidation.ValidationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_success() {
        UserDto dto = new UserDto();
        dto.setName("John");
        dto.setEmail("john@example.com");
        dto.setPassword("Password123!");
        dto.setRole("USER");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("hashed_password");

        userService.registerUser(dto);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_invalidEmail() {
        UserDto dto = new UserDto();
        dto.setEmail("invalid-email");
        dto.setPassword("Password123!");

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(dto));
    }

    @Test
    void testRegisterUser_invalidPassword() {
        UserDto dto = new UserDto();
        dto.setEmail("valid@example.com");
        dto.setPassword("123");

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(dto));
    }

    @Test
    void testRegisterUser_emailAlreadyExists() {
        UserDto dto = new UserDto();
        dto.setEmail("john@example.com");
        dto.setPassword("Password123!");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> userService.registerUser(dto));
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setName("John");
        user1.setEmail("john@example.com");
        user1.setRole("USER");

        User user2 = new User();
        user2.setName("Jane");
        user2.setEmail("jane@example.com");
        user2.setRole("ADMIN");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDto> users = userService.getAllUsers();

        assertEquals(2, users.size());
        assertEquals("john@example.com", users.get(0).getEmail());
        assertEquals("jane@example.com", users.get(1).getEmail());
    }

    @Test
    void testUpdateUserRole_success() {
        User user = new User();
        user.setId(1L);
        user.setRole("USER");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.updateUserRole(1L, "ADMIN");

        assertEquals("ADMIN", user.getRole());
        verify(userRepository).save(user);
    }
}
