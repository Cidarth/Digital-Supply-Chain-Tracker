// UserService.java
package Digital.Supply.tracker.service;

import Digital.Supply.tracker.entity.User;
import Digital.Supply.tracker.repository.UserRepository;
import Digital.Supply.tracker.dto.UserRegisterRequest;
import Digital.Supply.tracker.dto.UserLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User register(UserRegisterRequest req) {
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(req.getPassword())
                .role(User.Role.valueOf(req.getRole()))
                .build();
        return userRepository.save(user);
    }

    public User login(UserLoginRequest req) {
        return userRepository.findByEmail(req.getEmail())
                .filter(u -> u.getPassword().equals(req.getPassword()))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateRole(Long userId, String newRole) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setRole(User.Role.valueOf(newRole));
        userRepository.save(user);
    }
}
