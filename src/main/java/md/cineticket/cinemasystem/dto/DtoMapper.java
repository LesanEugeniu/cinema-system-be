package md.cineticket.cinemasystem.dto;

import md.cineticket.cinemasystem.model.*;
import org.mapstruct.Mapper;

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

    MovieDto toDto(Movie movie);

    Movie toEntity(MovieDto dto);

}
