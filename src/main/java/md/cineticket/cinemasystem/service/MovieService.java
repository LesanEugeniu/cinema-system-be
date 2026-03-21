package md.cineticket.cinemasystem.service;

import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.DtoMapper;
import md.cineticket.cinemasystem.dto.MovieDto;
import md.cineticket.cinemasystem.exception.CinemaException;
import md.cineticket.cinemasystem.model.Movie;
import md.cineticket.cinemasystem.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {

    @Value("${movie.image.upload-dir}")
    private String uploadDir;
    private final MovieRepository movieRepository;
    private final DirectorService directorService;
    private final ActorService actorService;
    private final DtoMapper dtoMapper;

    public MovieDto create(MovieDto dto) {
        Movie movie = dtoMapper.toEntity(dto);

        movie.setDirectors(directorService.findAllById(dto.getDirectorIds()));
        movie.setActors(actorService.findAllById(dto.getActorIds()));

        Movie saved = movieRepository.save(movie);
        return dtoMapper.toDto(saved);
    }

    public List<MovieDto> getAll(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .stream()
                .map(dtoMapper::toDto)
                .toList();
    }

    public MovieDto getById(Long id) {
        Movie movie = getEntityById(id);
        return dtoMapper.toDto(movie);
    }

    public Movie getEntityById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "Movie not found"));
    }

    public MovieDto update(Long id, MovieDto dto) {
        Movie movie = getEntityById(id);

        movie.setTitle(dto.getTitle());
        movie.setImagePath(dto.getImagePath());
        movie.setDescription(dto.getDescription());
        movie.setDurationMinutes(dto.getDurationMinutes());
        movie.setAgeRestriction(dto.getAgeRestriction());
        movie.setGenre(dto.getGenre());
        movie.setFormatType(dto.getFormatType());
        movie.setSoundFormat(dto.getSoundFormat());
        movie.setTrailerUrl(dto.getTrailerUrl());
        movie.setReleaseDate(dto.getReleaseDate());

        movie.setDirectors(directorService.findAllById(dto.getDirectorIds()));
        movie.setActors(actorService.findAllById(dto.getActorIds()));

        Movie updated = movieRepository.save(movie);
        return dtoMapper.toDto(updated);
    }

    public void delete(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new CinemaException(HttpStatus.BAD_REQUEST.value(), "Movie not found");
        }
        movieRepository.deleteById(id);
    }

    public String saveFile(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
            String filename = UUID.randomUUID() + extension;
            File dest = new File(dir, filename);
            file.transferTo(dest);
            return filename;
        }

        return null;
    }

}
