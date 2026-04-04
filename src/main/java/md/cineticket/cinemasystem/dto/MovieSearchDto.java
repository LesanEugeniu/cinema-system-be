package md.cineticket.cinemasystem.dto;

import lombok.Data;
import md.cineticket.cinemasystem.model.AudioLanguage;
import md.cineticket.cinemasystem.model.FormatType;
import md.cineticket.cinemasystem.model.Genre;
import md.cineticket.cinemasystem.model.SoundFormat;

import java.util.List;

@Data
public class MovieSearchDto {
    private List<Long> actorIds;
    private List<Long> directorIds;
    private SoundFormat soundFormat;
    private FormatType formatType;
    private AudioLanguage audioLanguage;
    private Genre genre;
}