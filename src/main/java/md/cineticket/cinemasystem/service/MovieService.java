package md.cineticket.cinemasystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.DtoMapper;
import md.cineticket.cinemasystem.dto.MovieByScreeningDto;
import md.cineticket.cinemasystem.dto.MovieDto;
import md.cineticket.cinemasystem.dto.MovieSearchDto;
import md.cineticket.cinemasystem.exception.CinemaException;
import md.cineticket.cinemasystem.model.Actor;
import md.cineticket.cinemasystem.model.Director;
import md.cineticket.cinemasystem.model.Movie;
import md.cineticket.cinemasystem.model.Screening;
import md.cineticket.cinemasystem.repo.MovieRepository;
import md.cineticket.cinemasystem.repo.ScreeningRepository;
import md.cineticket.cinemasystem.specification.MovieSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {

    @Value("${movie.image.upload-dir}")
    private String uploadDir;
    private final MovieRepository movieRepository;
    private final DirectorService directorService;
    private final ActorService actorService;
    private final DtoMapper dtoMapper;
    private final ScreeningRepository screeningRepository;

    public MovieDto create(MovieDto dto) {
        Movie movie = dtoMapper.toEntity(dto);

        List<Director> directors = directorService.findAllById(dto.getDirectorIds());
        directors.forEach(movie::addDirector);
        List<Actor> actors = actorService.findAllById(dto.getActorIds());
        actors.forEach(movie::addActor);

        Movie saved = movieRepository.save(movie);
        return dtoMapper.toDto(saved);
    }

    public List<MovieDto> getAll(Pageable pageable) {
        Page<Movie> movies = movieRepository.findAll(pageable);
        movies.stream().forEach(m -> {
            m.getActors().size();
            m.getDirectors().size();
        });

        return movies.stream()
                .map(dtoMapper::toDto)
                .toList();
    }

    public MovieDto getById(Long id) {
        Movie movie = getEntityById(id);
        return dtoMapper.toDto(movie);
    }

    public Movie getEntityById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "Movie not found"));
        movie.getActors().size();
        movie.getDirectors().size();

        return movie;
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

        List<Director> directors = directorService.findAllById(dto.getDirectorIds());
        directors.forEach(movie::addDirector);
        List<Actor> actors = actorService.findAllById(dto.getActorIds());
        actors.forEach(movie::addActor);


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

    public Resource getResource(String filename) throws MalformedURLException {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("File not found: " + filename);
            }

            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error reading file: " + filename, e);
        }
    }

    public String getContentType(String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            String contentType = Files.probeContentType(filePath);
            return contentType != null ? contentType : "application/octet-stream";
        } catch (Exception e) {
            return "application/octet-stream";
        }
    }

    public Page<MovieDto> search(MovieSearchDto dto, Pageable pageable) {
        Page<Movie> moviesPage = movieRepository.findAll(MovieSpecification.search(dto), pageable);
        return moviesPage.map(dtoMapper::toDto);
    }

    public List<MovieDto> getMoviesByScreeningRange(MovieByScreeningDto dto) {
        List<Screening> screenings = screeningRepository.findOverlappingScreenings(dto.getStartDate(), dto.getEndDate());

        return screenings.stream()
                .map(Screening::getMovie)
                .distinct()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }

}
