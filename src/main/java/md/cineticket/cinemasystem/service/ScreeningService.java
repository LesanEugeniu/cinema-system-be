package md.cineticket.cinemasystem.service;

import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.DtoMapper;
import md.cineticket.cinemasystem.dto.ScreeningDto;
import md.cineticket.cinemasystem.exception.CinemaException;
import md.cineticket.cinemasystem.model.Screening;
import md.cineticket.cinemasystem.repo.ScreeningRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final HallService hallService;
    private final MovieService movieService;
    private final DtoMapper dtoMapper;

    public ScreeningDto create(ScreeningDto dto) {
        Screening screening = dtoMapper.toEntity(dto);

        screening.setHall(hallService.getEntityById(dto.getHallId()));
        screening.setMovie(movieService.getEntityById(dto.getMovieId()));

        Screening saved = screeningRepository.save(screening);
        return dtoMapper.toDto(saved);
    }

    public List<ScreeningDto> getAll(Pageable pageable) {
        return screeningRepository.findAll(pageable)
                .stream()
                .map(dtoMapper::toDto)
                .toList();
    }

    public ScreeningDto getById(Long id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "Screening not found"));
        return dtoMapper.toDto(screening);
    }

    public ScreeningDto update(Long id, ScreeningDto dto) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "Screening not found"));

        if (dto.getHallId() != null) {
            screening.setHall(hallService.getEntityById(dto.getHallId()));
        }

        if (dto.getMovieId() != null) {
            screening.setMovie(movieService.getEntityById(dto.getMovieId()));
        }

        screening.setStartTime(dto.getStartTime());
        screening.setEndTime(dto.getEndTime());

        Screening updated = screeningRepository.save(screening);
        return dtoMapper.toDto(updated);
    }

    public void delete(Long id) {
        if (!screeningRepository.existsById(id)) {
            throw new CinemaException(HttpStatus.BAD_REQUEST.value(), "Screening not found");
        }
        screeningRepository.deleteById(id);
    }
}