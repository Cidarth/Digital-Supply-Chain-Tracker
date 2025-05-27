// AuthController.java
package Digital.Supply.tracker.controller;

import Digital.Supply.tracker.dto.UserRegisterRequest;
import Digital.Supply.tracker.dto.UserLoginRequest;
import Digital.Supply.tracker.entity.User;
import Digital.Supply.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody UserRegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public User login(@RequestBody UserLoginRequest request) {
        return userService.login(request);
    }
}
