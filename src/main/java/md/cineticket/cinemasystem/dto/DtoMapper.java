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

    @Mapping(target = "hallId", expression = "java(saved.getHall().getId())")
    SeatDto toDto(Seat saved);

    Seat toEntity(SeatDto dto);

    Screening toEntity(ScreeningDto dto);

    @Mapping(target = "movieId", expression = "java(saved.getMovie().getId())")
    @Mapping(target = "hallId", expression = "java(saved.getHall().getId())")
    @Mapping(target = "bookingIds", expression = "java(getScreeningIds(saved))")
    ScreeningDto toDto(Screening saved);

    Booking toEntity(BookingDto dto);

    @Mapping(target = "seatIds", expression = "java(getSeatIds(saved))")
    @Mapping(target = "screeningId", expression = "java(saved.getScreening().getId())")
    @Mapping(target = "userId", expression = "java(saved.getUser().getId())")
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

    default List<Long> getScreeningIds(Screening screening) {
        if (screening.getBookings() == null) return null;
        return screening.getBookings().stream()
                .map(Booking::getId)
                .collect(Collectors.toList());
    }

    default List<Long> getSeatIds(Booking booking) {
        if (booking.getSeats() == null) return null;
        return booking.getSeats().stream()
                .map(Seat::getId)
                .collect(Collectors.toList());
    }

}
