package md.cineticket.cinemasystem.utils;

import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.model.Role;
import md.cineticket.cinemasystem.model.User;
import md.cineticket.cinemasystem.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            User user = new User();
            user.setEmail("admin@mail.com");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRole(Role.ADMIN);

            userRepository.save(user);
        };
    }

}