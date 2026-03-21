package md.cineticket.cinemasystem.utils;

import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.RegisterRequest;
import md.cineticket.cinemasystem.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserService userService;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setEmail("admin@mail.com");
            registerRequest.setUsername("admin");
            registerRequest.setPassword("admin");

            userService.createUser(registerRequest);
        };
    }

}