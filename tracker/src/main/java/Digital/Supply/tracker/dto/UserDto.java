// UserRegisterRequest.java
package Digital.Supply.tracker.dto;

import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String email;
    private String password;
    private String role;
}
