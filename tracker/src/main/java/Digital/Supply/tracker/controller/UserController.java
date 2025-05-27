// UserController.java
package Digital.Supply.tracker.controller;

import Digital.Supply.tracker.entity.User;
import Digital.Supply.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}/role")
    public void updateRole(@PathVariable Long id, @RequestParam String role) {
        userService.updateRole(id, role);
    }
}
