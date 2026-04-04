package md.cineticket.cinemasystem.dto;

import md.cineticket.cinemasystem.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    ActorDto toDto(Actor actor);

    Actor toEntity(ActorDto dto);

    Director toEntity(DirectorDto dto);

    DirectorDto toDto(Director saved);

    Hall toEntity(HallDto dto);

    HallDto toDto(Hall saved);

    SeatDto toDto(Seat saved);

    Seat toEntity(SeatDto dto);

    Screening toEntity(ScreeningDto dto);

    ScreeningDto toDto(Screening saved);

    Booking toEntity(BookingDto dto);

    BookingDto toDto(Booking saved);

    @Mapping(target = "directorIds", expression = "java(getDirectorIds(movie))")
    @Mapping(target = "actorIds", expression = "java(getActorIds(movie))")
    MovieDto toDto(Movie movie);

    Movie toEntity(MovieDto dto);

    default List<Long> getDirectorIds(Movie movie) {
        if (movie.getDirectors() == null) return null;
        return movie.getDirectors().stream()
                .map(Director::getId)
                .collect(Collectors.toList());
    }

    default List<Long> getActorIds(Movie movie) {
        if (movie.getActors() == null) return null;
        return movie.getActors().stream()
                .map(Actor::getId)
                .collect(Collectors.toList());
    }

}
