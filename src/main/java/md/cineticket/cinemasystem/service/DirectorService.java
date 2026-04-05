package md.cineticket.cinemasystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.DirectorDto;
import md.cineticket.cinemasystem.dto.DtoMapper;
import md.cineticket.cinemasystem.exception.CinemaException;
import md.cineticket.cinemasystem.model.Director;
import md.cineticket.cinemasystem.model.Movie;
import md.cineticket.cinemasystem.repo.DirectorRepository;
import md.cineticket.cinemasystem.repo.MovieRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DirectorService {

    private final DirectorRepository directorRepository;

    private final MovieRepository movieRepository;

    private final DtoMapper dtoMapper;

    public DirectorDto create(DirectorDto dto) {
        Director director = dtoMapper.toEntity(dto);
        Director saved = directorRepository.save(director);
        return dtoMapper.toDto(saved);
    }

    public List<DirectorDto> getAll(Pageable pageable) {
        return directorRepository.findAll(pageable)
                .stream()
                .map(dtoMapper::toDto)
                .toList();
    }

    public DirectorDto getById(Long id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new CinemaException(
                        HttpStatus.BAD_REQUEST.value(),
                        "Director not found"
                ));
        return dtoMapper.toDto(director);
    }

    public DirectorDto update(Long id, DirectorDto dto) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new CinemaException(
                        HttpStatus.BAD_REQUEST.value(),
                        "Director not found"
                ));

        director.setName(dto.getName());

        if (dto.getMovieIds() != null) {
            List<Movie> movies = movieRepository.findAllById(dto.getMovieIds());
            director.setMovies(movies);
        }

        Director updated = directorRepository.save(director);
        return dtoMapper.toDto(updated);
    }

    public void delete(Long id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new CinemaException(
                        HttpStatus.BAD_REQUEST.value(), "Director not found"
                ));

        for (Movie movie : director.getMovies()) {
            movie.getDirectors().remove(director);
        }

        director.getMovies().clear();

        directorRepository.delete(director);
    }

    public List<Director> findAllById(List<Long> directorIds) {
        if (directorIds != null && !directorIds.isEmpty()) {
            List<Director> directors = directorRepository.findAllById(directorIds);
            if (directors.size() != directorIds.size()) {
                throw new CinemaException(HttpStatus.BAD_REQUEST.value(), "Some directors not found");
            }
            return directors;
        }

        return Collections.emptyList();
    }

}