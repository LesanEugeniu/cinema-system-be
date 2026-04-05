package md.cineticket.cinemasystem.utils;

import jakarta.transaction.Transactional;
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
            Actor actor3 = Actor.builder().name("Matthew McConaughey").build();
            Actor actor4 = Actor.builder().name("Robert Downey Jr.").build();
            Actor actor5 = Actor.builder().name("Scarlett Johansson").build();
            Actor actor6 = Actor.builder().name("Tom Holland").build();
            Actor actor7 = Actor.builder().name("Chris Hemsworth").build();
            Actor actor8 = Actor.builder().name("Mark Ruffalo").build();
            Actor actor9 = Actor.builder().name("Gal Gadot").build();
            Actor actor10 = Actor.builder().name("Henry Cavill").build();
            Actor actor11 = Actor.builder().name("Emma Stone").build();
            Actor actor12 = Actor.builder().name("Ryan Gosling").build();
            Actor actor13 = Actor.builder().name("Tom Hardy").build();
            Actor actor14 = Actor.builder().name("Charlize Theron").build();
            Actor actor17 = Actor.builder().name("Chris Evans").build();
            Actor actor19 = Actor.builder().name("Robert Pattinson").build();
            Actor actor20 = Actor.builder().name("Zoë Kravitz").build();
            Actor actor21 = Actor.builder().name("Timothée Chalamet").build();
            Actor actor22 = Actor.builder().name("Zendaya").build();
            Actor actor24 = Actor.builder().name("Jacob Tremblay").build();
            Actor actor25 = Actor.builder().name("Florence Pugh").build();
            Actor actor26 = Actor.builder().name("Anya Taylor-Joy").build();
            Actor actor27 = Actor.builder().name("Daniel Kaluuya").build();
            Actor actor28 = Actor.builder().name("John Boyega").build();
            Actor actor29 = Actor.builder().name("Saoirse Ronan").build();
            Actor actor30 = Actor.builder().name("Tim Blake Nelson").build();
            Actor actor31 = Actor.builder().name("Christian Bale").build();
            Actor actor32 = Actor.builder().name("John David Washington").build();

            actorRepository.saveAll(List.of(
                    actor13, actor14, actor17, actor1, actor2, actor3, actor4, actor5,
                    actor25, actor26, actor27, actor28, actor29, actor30, actor31,
                    actor19, actor20, actor21, actor22, actor6, actor24, actor32,
                    actor7, actor8, actor9, actor10, actor11, actor12)
            );

            // ================= DIRECTORS =================
            Director director1 = Director.builder().name("Christopher Nolan").build();
            Director director2 = Director.builder().name("Wachowski Sisters").build();
            Director director3 = Director.builder().name("Anthony Russo").build();
            Director director4 = Director.builder().name("Joe Russo").build();
            Director director5 = Director.builder().name("Jon Favreau").build();
            Director director9 = Director.builder().name("Ridley Scott").build();
            Director director10 = Director.builder().name("Patty Jenkins").build();
            Director director6 = Director.builder().name("James Cameron").build();
            Director director7 = Director.builder().name("Zack Snyder").build();
            Director director8 = Director.builder().name("Damien Chazelle").build();
            Director director12 = Director.builder().name("Matt Reeves").build();
            Director director13 = Director.builder().name("Denis Villeneuve").build();
            Director director14 = Director.builder().name("Jon Watts").build();
            Director director15 = Director.builder().name("Jordan Peele").build();
            Director director16 = Director.builder().name("Edgar Wright").build();
            Director director17 = Director.builder().name("Greta Gerwig").build();
            Director director18 = Director.builder().name("Wes Anderson").build();
            Director director19 = Director.builder().name("Daniel Kwan & Daniel Scheinert").build();
            Director director20 = Director.builder().name("Scott Frank").build();
            Director director21 = Director.builder().name("Ari Aster").build();

            directorRepository.saveAll(List.of(
                    director15, director16, director17, director18, director19, director20, director21,
                    director12, director13, director14, director6, director7, director8,
                    director9, director10, director1, director2, director3, director4, director5)
            );

            // ================= MOVIES EXTENDED =================
            Movie movie1 = Movie.builder()
                    .title("Inception")
                    .imagePath("inception.jpg")
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
                    .trailerUrl("https://www.youtube.com/watch?v=YoHD9XEInc0")
                    .build();

            Movie movie2 = Movie.builder()
                    .title("Matrix")
                    .imagePath("matrix.png")
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
                    .trailerUrl("https://www.youtube.com/watch?v=ywmaUcIH30w")
                    .build();

            Movie movie3 = Movie.builder()
                    .title("Interstellar")
                    .imagePath("interstellar.jpg")
                    .description("Space exploration and time dilation")
                    .durationMinutes(169)
                    .ageRestriction(13)
                    .genre(Genre.SCI_FI)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2014, 11, 7))
                    .directors(List.of(director1))
                    .actors(List.of(actor3))
                    .trailerUrl("https://www.youtube.com/watch?v=EXeTwQWrcwY")
                    .build();

            Movie movie4 = Movie.builder()
                    .title("John Wick")
                    .imagePath("johnwick.jpg")
                    .description("Action thriller with Keanu Reeves")
                    .durationMinutes(101)
                    .ageRestriction(16)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.STEREO)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2014, 10, 24))
                    .directors(List.of(director2))
                    .actors(List.of(actor2))
                    .trailerUrl("https://www.youtube.com/watch?v=8hP9D6kZseM")
                    .build();

            Movie movie5 = Movie.builder()
                    .title("Avengers: Infinity War")
                    .imagePath("infinitywar.jpg")
                    .description("Superheroes fight to save the universe")
                    .durationMinutes(149)
                    .ageRestriction(13)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.THREE_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN, AudioLanguage.RU))
                    .releaseDate(LocalDate.of(2018, 4, 27))
                    .directors(List.of(director3, director4))
                    .actors(List.of(actor4, actor5, actor6))
                    .trailerUrl("https://www.youtube.com/watch?v=6ZfuNTqbHE8")
                    .build();

            Movie movie6 = Movie.builder()
                    .title("The Dark Knight")
                    .imagePath("darkknight.jpg")
                    .description("Batman faces the Joker in Gotham City")
                    .durationMinutes(152)
                    .ageRestriction(13)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2008, 7, 18))
                    .directors(List.of(director1))
                    .actors(List.of(actor31))
                    .trailerUrl("https://www.youtube.com/watch?v=EXeTwQWrcwY")
                    .build();

            Movie movie7 = Movie.builder()
                    .title("Iron Man")
                    .imagePath("ironman.jpg")
                    .description("Tony Stark becomes Iron Man")
                    .durationMinutes(126)
                    .ageRestriction(13)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.STEREO)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2008, 5, 2))
                    .directors(List.of(director5))
                    .actors(List.of(actor4))
                    .trailerUrl("https://www.youtube.com/watch?v=8hP9D6kZseM")
                    .build();

            Movie movie8 = Movie.builder()
                    .title("Spider-Man: No Way Home")
                    .imagePath("spiderman.jpg")
                    .description("Spider-Man faces multiverse villains")
                    .durationMinutes(148)
                    .ageRestriction(13)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.THREE_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2021, 12, 17))
                    .directors(List.of(director5))
                    .actors(List.of(actor6, actor5))
                    .trailerUrl("https://www.youtube.com/watch?v=JfVOs4VSpmA")
                    .build();

            Movie movie9 = Movie.builder()
                    .title("Dune")
                    .imagePath("dune.jpg")
                    .description("Epic sci-fi adventure on Arrakis")
                    .durationMinutes(155)
                    .ageRestriction(13)
                    .genre(Genre.SCI_FI)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2021, 10, 22))
                    .directors(List.of(director13))
                    .actors(List.of(actor21))
                    .trailerUrl("https://www.youtube.com/watch?v=n9xhJrPXop4")
                    .build();

            Movie movie10 = Movie.builder()
                    .title("Tenet")
                    .imagePath("tenet.jpg")
                    .description("Time inversion spy thriller")
                    .durationMinutes(150)
                    .ageRestriction(13)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2020, 8, 26))
                    .directors(List.of(director1))
                    .actors(List.of(actor32))
                    .trailerUrl("https://www.youtube.com/watch?v=L3pk_TBkihU")
                    .build();

            Movie movie11 = Movie.builder()
                    .title("Avatar: The Way of Water")
                    .imagePath("avatar2.jpg")
                    .description("Epic science fiction adventure on Pandora")
                    .durationMinutes(192)
                    .ageRestriction(13)
                    .genre(Genre.SCI_FI)
                    .formatType(FormatType.THREE_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2022, 12, 16))
                    .directors(List.of(director6))
                    .actors(List.of(actor7, actor8))
                    .trailerUrl("https://www.youtube.com/watch?v=d9MyW72ELq0")
                    .build();

            Movie movie12 = Movie.builder()
                    .title("Justice League")
                    .imagePath("justiceleague.jpg")
                    .description("Superheroes unite to save the world")
                    .durationMinutes(120)
                    .ageRestriction(13)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2017, 11, 17))
                    .directors(List.of(director7))
                    .actors(List.of(actor9, actor10))
                    .trailerUrl("https://www.youtube.com/watch?v=3cxixDgHUYw")
                    .build();

            Movie movie13 = Movie.builder()
                    .title("La La Land")
                    .imagePath("lalaland.png")
                    .description("Romantic musical set in Los Angeles")
                    .durationMinutes(128)
                    .ageRestriction(10)
                    .genre(Genre.DRAMA)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.STEREO)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2016, 12, 9))
                    .directors(List.of(director8))
                    .actors(List.of(actor11, actor12))
                    .trailerUrl("https://www.youtube.com/watch?v=0pdqf4P9MB8")
                    .build();

            Movie movie14 = Movie.builder()
                    .title("Wonder Woman")
                    .imagePath("wonderwoman.jpg")
                    .description("Diana discovers her full powers and destiny")
                    .durationMinutes(141)
                    .ageRestriction(13)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2017, 6, 2))
                    .directors(List.of(director7))
                    .actors(List.of(actor9))
                    .trailerUrl("https://www.youtube.com/watch?v=1Q8fG0TtVAY")
                    .build();

            Movie movie15 = Movie.builder()
                    .title("Blade Runner 2049")
                    .imagePath("bladerunner2049.jpg")
                    .description("A young blade runner discovers a long-buried secret")
                    .durationMinutes(164)
                    .ageRestriction(16)
                    .genre(Genre.SCI_FI)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2017, 10, 6))
                    .directors(List.of(director13))
                    .actors(List.of(actor12))
                    .trailerUrl("https://www.youtube.com/watch?v=gCcx85zbxz4")
                    .build();

            Movie movie16 = Movie.builder()
                    .title("Mad Max: Fury Road")
                    .imagePath("madmax.jpg")
                    .description("Post-apocalyptic action thriller in a desert world")
                    .durationMinutes(120)
                    .ageRestriction(16)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2015, 5, 15))
                    .directors(List.of(director9))
                    .actors(List.of(actor13, actor14))
                    .trailerUrl("https://www.youtube.com/watch?v=hEJnMQG9ev8")
                    .build();

            Movie movie17 = Movie.builder()
                    .title("Iron Man 2")
                    .imagePath("ironman2.jpg")
                    .description("Tony Stark faces new challenges and enemies")
                    .durationMinutes(124)
                    .ageRestriction(13)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.STEREO)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2010, 5, 7))
                    .directors(List.of(director5))
                    .actors(List.of(actor4, actor17))
                    .trailerUrl("https://www.youtube.com/watch?v=wKtcmiifycU")
                    .build();

            Movie movie18 = Movie.builder()
                    .title("Black Widow")
                    .imagePath("blackwidow.jpg")
                    .description("Natasha Romanoff confronts her past")
                    .durationMinutes(134)
                    .ageRestriction(13)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.THREE_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2021, 7, 9))
                    .directors(List.of(director10))
                    .actors(List.of(actor5))
                    .trailerUrl("https://www.youtube.com/watch?v=Fp9pNPdNwjI")
                    .build();

            Movie movie19 = Movie.builder()
                    .title("Thor: Ragnarok")
                    .imagePath("thor_ragnarok.jpg")
                    .description("Thor must escape and save Asgard from Hela")
                    .durationMinutes(130)
                    .ageRestriction(13)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.THREE_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2017, 11, 3))
                    .directors(List.of(director7))
                    .actors(List.of(actor7, actor17))
                    .trailerUrl("https://www.youtube.com/watch?v=ue80QwXMRHg")
                    .build();

            Movie movie20 = Movie.builder()
                    .title("Wonder Woman 1984")
                    .imagePath("wonderwoman1984.png")
                    .description("Diana Prince faces a new villain in the 1980s")
                    .durationMinutes(151)
                    .ageRestriction(13)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2020, 12, 25))
                    .directors(List.of(director10))
                    .actors(List.of(actor9, actor9))
                    .trailerUrl("https://www.youtube.com/watch?v=sfM7_JLk-84")
                    .build();

            Movie movie21 = Movie.builder()
                    .title("The Batman")
                    .imagePath("thebatman.jpg")
                    .description("Batman uncovers corruption in Gotham City while facing the Riddler")
                    .durationMinutes(176)
                    .ageRestriction(13)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2022, 3, 4))
                    .directors(List.of(director12))
                    .actors(List.of(actor19, actor20))
                    .trailerUrl("https://www.youtube.com/watch?v=mqqft2x_Aa4")
                    .build();

            Movie movie22 = Movie.builder()
                    .title("Dune Part One")
                    .imagePath("dune2021.jpg")
                    .description("A young duke's son must protect the desert planet Arrakis")
                    .durationMinutes(155)
                    .ageRestriction(13)
                    .genre(Genre.SCI_FI)
                    .formatType(FormatType.THREE_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2021, 10, 22))
                    .directors(List.of(director13))
                    .actors(List.of(actor21))
                    .trailerUrl("https://www.youtube.com/watch?v=n9xhJrPXop4")
                    .build();

            Movie movie23 = Movie.builder()
                    .title("Spider-Man: No Way Home")
                    .imagePath("spiderman_nwh.jpeg")
                    .description("Spider-Man faces villains from the multiverse")
                    .durationMinutes(148)
                    .ageRestriction(13)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.THREE_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2021, 12, 17))
                    .directors(List.of(director14))
                    .actors(List.of(actor6, actor22))
                    .trailerUrl("https://www.youtube.com/watch?v=JfVOs4VSpmA")
                    .build();

            Movie movie24 = Movie.builder()
                    .title("The French Dispatch")
                    .imagePath("french_dispatch.jpeg")
                    .description("Stories from an American newspaper in a fictional French city")
                    .durationMinutes(108)
                    .ageRestriction(12)
                    .genre(Genre.DRAMA)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.STEREO)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2021, 10, 22))
                    .directors(List.of(director18))
                    .actors(List.of(actor21, actor24))
                    .trailerUrl("https://www.youtube.com/watch?v=ecX1bDFNLoI")
                    .build();

            Movie movie25 = Movie.builder()
                    .title("Everything Everywhere All At Once")
                    .imagePath("eeaa.jpg")
                    .description("A woman discovers multiple universes and her role in them")
                    .durationMinutes(139)
                    .ageRestriction(13)
                    .genre(Genre.SCI_FI)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2022, 3, 11))
                    .directors(List.of(director19))
                    .actors(List.of(actor22, actor24))
                    .trailerUrl("https://www.youtube.com/watch?v=wxN1T1uxQ2g")
                    .build();
            Movie movie26 = Movie.builder()
                    .title("Get Out")
                    .imagePath("getout.jpg")
                    .description("A young African-American man uncovers a disturbing secret at his girlfriend's family estate")
                    .durationMinutes(104)
                    .ageRestriction(16)
                    .genre(Genre.HORROR)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2017, 2, 24))
                    .directors(List.of(director15))
                    .actors(List.of(actor27))
                    .trailerUrl("https://www.youtube.com/watch?v=DzfpyUB60YY")
                    .build();

            Movie movie27 = Movie.builder()
                    .title("Baby Driver")
                    .imagePath("babydriver.jpg")
                    .description("A young getaway driver relies on the beat of his personal soundtrack to be the best in the game")
                    .durationMinutes(113)
                    .ageRestriction(13)
                    .genre(Genre.ACTION)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.STEREO)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2017, 6, 28))
                    .directors(List.of(director16))
                    .actors(List.of(actor28))
                    .trailerUrl("https://www.youtube.com/watch?v=z2z857RSfhk")
                    .build();

            Movie movie28 = Movie.builder()
                    .title("Little Women")
                    .imagePath("littlewomen.jpg")
                    .description("Coming-of-age story following the lives of the March sisters in post-Civil War America")
                    .durationMinutes(135)
                    .ageRestriction(10)
                    .genre(Genre.DRAMA)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.STEREO)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2019, 12, 25))
                    .directors(List.of(director17))
                    .actors(List.of(actor29))
                    .trailerUrl("https://www.youtube.com/watch?v=AST2-4db4ic")
                    .build();

            Movie movie29 = Movie.builder()
                    .title("The Queen's Gambit")
                    .imagePath("queensgambit.png")
                    .description("A young chess prodigy rises to the top while struggling with personal issues")
                    .durationMinutes(150)
                    .ageRestriction(12)
                    .genre(Genre.DRAMA)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.STEREO)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2020, 10, 23))
                    .directors(List.of(director20))
                    .actors(List.of(actor26))
                    .trailerUrl("https://www.youtube.com/watch?v=CDrieqwSdgI")
                    .build();

            Movie movie30 = Movie.builder()
                    .title("Midsommar")
                    .imagePath("midsommar.png")
                    .description("A couple travels to Sweden for a fabled midsummer festival, where things take a sinister turn")
                    .durationMinutes(147)
                    .ageRestriction(18)
                    .genre(Genre.HORROR)
                    .formatType(FormatType.TWO_D)
                    .soundFormat(SoundFormat.SURROUND)
                    .audioLanguages(List.of(AudioLanguage.EN))
                    .releaseDate(LocalDate.of(2019, 7, 3))
                    .directors(List.of(director21))
                    .actors(List.of(actor25))
                    .trailerUrl("https://www.youtube.com/watch?v=1Vnghdsjmd0")
                    .build();

            movieRepository.saveAll(List.of(movie26, movie27, movie28, movie29, movie30));
            movieRepository.saveAll(List.of(movie21, movie22, movie23, movie24, movie25));
            movieRepository.saveAll(List.of(movie16, movie17, movie18, movie19, movie20));
            movieRepository.saveAll(List.of(movie11, movie12, movie13, movie14, movie15));
            movieRepository.saveAll(List.of(
                    movie1, movie2, movie3, movie4, movie5,
                    movie6, movie7, movie8, movie9, movie10
            ));

            // ================= HALL =================
            Hall hall = Hall.builder()
                    .name("Hall 1")
                    .totalRows(5)
                    .seatsPerRow(5)
                    .build();

            Hall hall2 = Hall.builder()
                    .name("Hall 2")
                    .totalRows(12)
                    .seatsPerRow(15)
                    .build();

            Hall hall3 = Hall.builder()
                    .name("Hall 3")
                    .totalRows(10)
                    .seatsPerRow(12)
                    .build();

            Hall hall4 = Hall.builder()
                    .name("Hall 4")
                    .totalRows(15)
                    .seatsPerRow(18)
                    .build();

            Hall hall5 = Hall.builder()
                    .name("Hall 5")
                    .totalRows(14)
                    .seatsPerRow(16)
                    .build();

            hallRepository.saveAll(List.of(hall, hall2, hall3, hall4, hall5));

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

            List<Seat> newSeats = new ArrayList<>();

            for (Hall halll : List.of(hall2, hall3, hall4, hall5)) {
                for (int row = 1; row <= halll.getTotalRows(); row++) {
                    for (int seatNum = 1; seatNum <= halll.getSeatsPerRow(); seatNum++) {
                        newSeats.add(Seat.builder()
                                .rowNumber(row)
                                .seatNumber(seatNum)
                                .hall(halll)
                                .build());
                    }
                }
            }

            seatRepository.saveAll(newSeats);
            seatRepository.saveAll(seats);

            // ================= SCREENINGS =================
            List<Hall> halls = hallRepository.findAll();
            List<Movie> movies = movieRepository.findAll();

            List<Screening> screenings = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            int screeningsPerDay = 3;

            for (int day = 0; day <= 7; day++) {
                LocalDateTime dayDate = now.plusDays(day);

                for (Hall hallScreen : halls) {
                    for (int i = 0; i < screeningsPerDay; i++) {
                        Movie movie = movies.get((int) (Math.random() * movies.size()));

                        int startHour = 10 + i * 3;
                        LocalDateTime startTime = dayDate.withHour(startHour).withMinute(0);
                        LocalDateTime endTime = startTime.plusMinutes(movie.getDurationMinutes());

                        screenings.add(Screening.builder()
                                .movie(movie)
                                .hall(hallScreen)
                                .startTime(startTime)
                                .endTime(endTime)
                                .build());
                    }
                }
            }

            screeningRepository.saveAll(screenings);
        };
    }
}