package md.cineticket.cinemasystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 200)
    @EqualsAndHashCode.Include
    private String title;

    @Column(name = "image_path", length = 500)
    @EqualsAndHashCode.Include
    private String imagePath;

    @Column(columnDefinition = "TEXT")
    @EqualsAndHashCode.Include
    private String description;

    @Column(name = "duration_minutes", nullable = false)
    @EqualsAndHashCode.Include
    private Integer durationMinutes;

    @Column(name = "age_restriction")
    @EqualsAndHashCode.Include
    private Integer ageRestriction;

    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    private Genre genre;

    @ElementCollection(targetClass = AudioLanguage.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "movie_audio_languages", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "audio_language")
    private List<AudioLanguage> audioLanguages = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    private FormatType formatType;

    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    private SoundFormat soundFormat;

    @Column(name = "trailer_url", length = 500)
    @EqualsAndHashCode.Include
    private String trailerUrl;

    @Column(name = "release_date")
    @EqualsAndHashCode.Include
    private LocalDate releaseDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_director",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    @Builder.Default
    private List<Director> directors = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    @Builder.Default
    private List<Actor> actors = new ArrayList<>();

    public void addDirector(Director director) {
        this.getDirectors().add(director);
        director.getMovies().add(this);
    }

    public void addActor(Actor actor) {
        this.getActors().add(actor);
        actor.getMovies().add(this);
    }

}