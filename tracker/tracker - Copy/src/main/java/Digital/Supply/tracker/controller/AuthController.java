package Digital.Supply.tracker.controller;

import Digital.Supply.tracker.dto.UserDto;
import Digital.Supply.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
            );
            // If authentication is successful
            return ResponseEntity.ok("Login successful");
        } catch (AuthenticationException ex) {
            // If authentication fails (wrong email or password)
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
    @PostMapping("/register")
    public String register(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return "User registered successfully";
    }
}
