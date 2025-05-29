package Digital.Supply.tracker.config;

import Digital.Supply.tracker.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    // BCrypt for secure password encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Load users from the database for authentication
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole()) // Make sure user.getRole() returns e.g. "ADMIN", "SUPPLIER"
                    .build();
        };
    }

    // HTTP Security configuration
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for REST APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        .requestMatchers("/api/items/**").hasAnyRole("ADMIN", "SUPPLIER")
                        .requestMatchers("/api/shipments/**").hasAnyRole("ADMIN", "SUPPLIER", "TRANSPORTER", "MANAGER")
                        .requestMatchers("/api/checkpoints/**").hasAnyRole("TRANSPORTER", "MANAGER")
                        .requestMatchers("/api/alerts/**").hasRole("ADMIN")
                        .requestMatchers("/api/reports/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(); // Use HTTP Basic Auth
        return http.build();
    }
}
