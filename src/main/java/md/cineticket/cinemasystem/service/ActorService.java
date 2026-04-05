package md.cineticket.cinemasystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.ActorDto;
import md.cineticket.cinemasystem.dto.DtoMapper;
import md.cineticket.cinemasystem.exception.CinemaException;
import md.cineticket.cinemasystem.model.Actor;
import md.cineticket.cinemasystem.model.Movie;
import md.cineticket.cinemasystem.repo.ActorRepository;
import md.cineticket.cinemasystem.repo.MovieRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ActorService {

    private final ActorRepository actorRepository;
    private final DtoMapper dtoMapper;
    private final MovieRepository movieRepository;

    public ActorDto create(ActorDto dto) {
        Actor actor = dtoMapper.toEntity(dto);
        Actor saved = actorRepository.save(actor);
        return dtoMapper.toDto(saved);
    }

    public List<ActorDto> getAll(Pageable pageable) {
        return actorRepository.findAll(pageable)
                .stream()
                .map(dtoMapper::toDto)
                .toList();
    }

    public ActorDto getById(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "Actor not found"));
        return dtoMapper.toDto(actor);
    }

    public ActorDto update(Long id, ActorDto dto) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "Actor not found"));

        actor.setName(dto.getName());
        Actor updated = actorRepository.save(actor);

        return dtoMapper.toDto(updated);
    }

    public void delete(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new CinemaException(
                        HttpStatus.BAD_REQUEST.value(), "Actor not found"
                ));

        for (Movie movie : actor.getMovies()) {
            movie.getActors().remove(actor);
        }

        actor.getMovies().clear();

        actorRepository.delete(actor);
    }

    public List<Actor> findAllById(List<Long> actorIds) {
        if (actorIds != null && !actorIds.isEmpty()) {
            List<Actor> actors = actorRepository.findAllById(actorIds);
            if (actors.size() != actorIds.size()) {
                throw new CinemaException(HttpStatus.BAD_REQUEST.value(), "Some actors not found");
            }
            return actors;
        }

        return Collections.emptyList();
    }

}
