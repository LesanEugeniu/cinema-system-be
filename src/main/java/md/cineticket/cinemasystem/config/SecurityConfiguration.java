package md.cineticket.cinemasystem.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static md.cineticket.cinemasystem.model.Role.ADMIN;
import static md.cineticket.cinemasystem.model.Role.USER;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] API_LIST_URL_PERMIT_ALL = {
            "/api/v1/actors/**",
            "/api/v1/actors",
            "/api/v1/directors/**",
            "/api/v1/directors",
            "/api/v1/movies/**",
            "/api/v1/movies",
            "/api/v1/screenings/**",
            "/api/v1/screenings",
    };

    private static final String[] API_LIST_URL = {
            "/api/v1/actors/**",
            "/api/v1/actors",
            "/api/v1/bookings/**",
            "/api/v1/bookings",
            "/api/v1/directors/**",
            "/api/v1/directors",
            "/api/v1/halls/**",
            "/api/v1/halls",
            "/api/v1/movies/**",
            "/api/v1/movies",
            "/api/v1/screenings/**",
            "/api/v1/screenings",
            "/api/v1/seats/**",
            "/api/v1/seats",
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {})
                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers("/api/v1/auth/**").permitAll()

                                // public GET endpoints
                                .requestMatchers(HttpMethod.GET, API_LIST_URL_PERMIT_ALL).permitAll()

                                .requestMatchers(HttpMethod.POST, "/api/v1/movies/search").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/movies/by-screening").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/bookings").permitAll()

                                // admin only
                                .requestMatchers(HttpMethod.POST, API_LIST_URL).hasRole(ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, API_LIST_URL).hasRole(ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, API_LIST_URL).hasRole(ADMIN.name())

                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication)
                                        -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}