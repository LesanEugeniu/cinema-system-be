package md.cineticket.cinemasystem.utils;

import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.model.*;
import md.cineticket.cinemasystem.repo.*;
import md.cineticket.cinemasystem.security.SeatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;
    private final SeatRepository seatRepository;
    private final ScreeningRepository screeningRepository;
    private final BookingRepository bookingRepository;

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initData() {
        return args -> {

            // ================= USERS =================
            User admin = User.builder()
                    .email("admin@mail.com")
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ADMIN)
                    .build();

            User user = User.builder()
                    .email("user@mail.com")
                    .username("user")
                    .password(passwordEncoder.encode("user"))
                    .role(Role.USER)
                    .build();

            userRepository.saveAll(List.of(admin, user));

            // ================= ACTORS =================
            Actor actor1 = Actor.builder().name("Leonardo DiCaprio").build();
            Actor actor2 = Actor.builder().name("Keanu Reeves").build();

            actorRepository.saveAll(List.of(actor1, actor2));

            // ================= DIRECTORS =================
            Director director1 = Director.builder().name("Christopher Nolan").build();
            Director director2 = Director.builder().name("Wachowski Sisters").build();

            directorRepository.saveAll(List.of(director1, director2));

            // ================= MOVIES =================
            Movie movie1 = Movie.builder()
                    .title("Inception")
                    .description("A mind-bending thriller")
                    .durationMinutes(148)
                    .ageRestriction(13)
                    .genre(Genre.SCI_FI)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN, AudioLanguage.RU))
                    .releaseDate(LocalDate.of(2010, 7, 16))
                    .directors(List.of(director1))
                    .actors(List.of(actor1))
                    .build();

            Movie movie2 = Movie.builder()
                    .title("Matrix")
                    .description("Simulation reality movie")
                    .durationMinutes(136)
                    .ageRestriction(16)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.THREE_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(1999, 3, 31))
                    .directors(List.of(director2))
                    .actors(List.of(actor2))
                    .build();

            movieRepository.saveAll(List.of(movie1, movie2));

            // ================= HALL =================
            Hall hall = Hall.builder()
                    .name("Hall 1")
                    .totalRows(5)
                    .seatsPerRow(5)
                    .build();

            hallRepository.save(hall);

            // ================= SEATS =================
            List<Seat> seats = new ArrayList<>();

            for (int row = 1; row <= hall.getTotalRows(); row++) {
                for (int seatNum = 1; seatNum <= hall.getSeatsPerRow(); seatNum++) {
                    seats.add(
                            Seat.builder()
                                    .rowNumber(row)
                                    .seatNumber(seatNum)
                                    .hall(hall)
                                    .build()
                    );
                }
            }

            seatRepository.saveAll(seats);

            // ================= SCREENING =================
            Screening screening = Screening.builder()
                    .movie(movie1)
                    .hall(hall)
                    .startTime(LocalDateTime.now().plusDays(1))
                    .endTime(LocalDateTime.now().plusDays(3))
                    .build();

            screeningRepository.save(screening);

            // ================= BOOKING =================
            Booking booking = Booking.builder()
                    .user(user)
                    .screening(screening)
                    .bookingDate(LocalDateTime.now())
                    .seats(seats.subList(0, 2))
                    .totalPrice(20.0)
                    .build();

            bookingRepository.save(booking);

        };
    }
}