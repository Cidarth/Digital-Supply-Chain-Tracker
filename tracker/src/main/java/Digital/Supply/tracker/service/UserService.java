package Digital.Supply.tracker.service;

import Digital.Supply.tracker.dto.UserDto;
import Digital.Supply.tracker.entity.User;
import Digital.Supply.tracker.exception.EmailAlreadyExistsException;
import Digital.Supply.tracker.repository.UserRepository;
import Digital.Supply.tracker.regexValidation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public void registerUser(UserDto userDto) {
        if (!ValidationUtils.isValidEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Invalid Email Format");
        }
        if (!ValidationUtils.isValidPassword(userDto.getPassword())) {
            throw new IllegalArgumentException("Invalid Password! Password criteria not matched.");
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("User already exists. Please login");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        userRepository.save(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public void updateUserRole(Long id, String role) {
        User user = userRepository.findById(id).orElseThrow();
        user.setRole(role);
        userRepository.save(user);
    }

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }
}
