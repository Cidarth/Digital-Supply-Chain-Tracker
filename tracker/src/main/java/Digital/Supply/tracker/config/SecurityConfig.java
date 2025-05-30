package Digital.Supply.tracker.config;

import Digital.Supply.tracker.repository.UserRepository;
import Digital.Supply.tracker.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                        .requestMatchers("/api/users/").hasRole("ADMIN")
                        .requestMatchers("/api/items/").hasAnyRole("ADMIN", "SUPPLIER")
                        .requestMatchers("/api/shipments/").hasAnyRole("ADMIN", "SUPPLIER", "TRANSPORTER", "MANAGER")
                        .requestMatchers("/api/checkpoints/").hasAnyRole("TRANSPORTER", "ADMIN", "MANAGER")
                        .requestMatchers("/api/alerts/").hasRole("ADMIN")
                        .requestMatchers("/api/reports/").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(); // or use .formLogin() if you want a login page

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole()) // make sure user.getRole() returns role name without "ROLE_" prefix
                    .build();
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
