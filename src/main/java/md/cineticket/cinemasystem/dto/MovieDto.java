package md.cineticket.cinemasystem.dto;

import lombok.*;
import md.cineticket.cinemasystem.model.AudioLanguage;
import md.cineticket.cinemasystem.model.FormatType;
import md.cineticket.cinemasystem.model.Genre;
import md.cineticket.cinemasystem.model.SoundFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {
    private Long id;
    private String title;
    private String imagePath;
    private String description;
    private Integer durationMinutes;
    private Integer ageRestriction;
    private Genre genre;
    private List<AudioLanguage> audioLanguages = new ArrayList<>();
    private FormatType formatType;
    private SoundFormat soundFormat;
    private String trailerUrl;
    private LocalDate releaseDate;
    private List<Long> directorIds = new ArrayList<>();
    private List<Long> actorIds = new ArrayList<>();
}