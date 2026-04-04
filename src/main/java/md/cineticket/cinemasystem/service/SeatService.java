package md.cineticket.cinemasystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.DtoMapper;
import md.cineticket.cinemasystem.dto.SeatDto;
import md.cineticket.cinemasystem.exception.CinemaException;
import md.cineticket.cinemasystem.model.Hall;
import md.cineticket.cinemasystem.model.Seat;
import md.cineticket.cinemasystem.repo.HallRepository;
import md.cineticket.cinemasystem.security.SeatRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatService {

    private final SeatRepository seatRepository;

    private final HallRepository hallRepository;

    private final DtoMapper dtoMapper;

    public SeatDto create(SeatDto dto) {
        Seat seat = dtoMapper.toEntity(dto);

        Hall hall = hallRepository.findById(dto.getHallId())
                .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "Hall not found"));
        seat.setHall(hall);

        Seat saved = seatRepository.save(seat);
        return dtoMapper.toDto(saved);
    }

    public List<SeatDto> getAll(Pageable pageable) {
        return seatRepository.findAll(pageable)
                .stream()
                .map(dtoMapper::toDto)
                .toList();
    }

    public SeatDto getById(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "Seat not found"));
        return dtoMapper.toDto(seat);
    }

    public SeatDto update(Long id, SeatDto dto) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "Seat not found"));

        seat.setRowNumber(dto.getRowNumber());
        seat.setSeatNumber(dto.getSeatNumber());

        if (dto.getHallId() != null) {
            Hall hall = hallRepository.findById(dto.getHallId())
                    .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "Hall not found"));
            seat.setHall(hall);
        }

        Seat updated = seatRepository.save(seat);
        return dtoMapper.toDto(updated);
    }

    public void delete(Long id) {
        if (!seatRepository.existsById(id)) {
            throw new CinemaException(HttpStatus.BAD_REQUEST.value(), "Seat not found");
        }
        seatRepository.deleteById(id);
    }

}