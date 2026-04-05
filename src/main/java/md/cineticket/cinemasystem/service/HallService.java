package md.cineticket.cinemasystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.DtoMapper;
import md.cineticket.cinemasystem.dto.HallDto;
import md.cineticket.cinemasystem.exception.CinemaException;
import md.cineticket.cinemasystem.model.Hall;
import md.cineticket.cinemasystem.model.Seat;
import md.cineticket.cinemasystem.repo.HallRepository;
import md.cineticket.cinemasystem.security.SeatRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HallService {

    private final HallRepository hallRepository;
    private final SeatRepository seatRepository;
    private final DtoMapper dtoMapper;

    public HallDto create(HallDto dto) {
        Hall hall = dtoMapper.toEntity(dto);
        Hall saved = hallRepository.save(hall);

        buildSeats(hall);
        return dtoMapper.toDto(saved);
    }

    private void buildSeats(Hall hall) {
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= hall.getTotalRows(); row++) {
            for (int seatNum = 1; seatNum <= hall.getSeatsPerRow(); seatNum++) {
                seats.add(
                        Seat.builder()
                                .rowNumber(row)
                                .seatNumber(seatNum)
                                .hall(hall)
                                .build()
                );
            }
        }

        seatRepository.saveAll(seats);
    }

    public List<HallDto> getAll(Pageable pageable) {
        return hallRepository.findAll(pageable)
                .stream()
                .map(dtoMapper::toDto)
                .toList();
    }

    public HallDto getById(Long id) {
        Hall hall = getEntityById(id);
        return dtoMapper.toDto(hall);
    }

    public Hall getEntityById(Long id) {
        return hallRepository.findById(id)
                .orElseThrow(() -> new CinemaException(
                        HttpStatus.BAD_REQUEST.value(),
                        "Hall not found"
                ));
    }

    public HallDto update(Long id, HallDto dto) {
        Hall hall = getEntityById(id);
        seatRepository.deleteAllByHall_Id(hall.getId());

        hall.setName(dto.getName());
        hall.setTotalRows(dto.getTotalRows());
        hall.setSeatsPerRow(dto.getSeatsPerRow());
        Hall updated = hallRepository.save(hall);
        buildSeats(updated);

        return dtoMapper.toDto(updated);
    }

    public void delete(Long id) {
        if (!hallRepository.existsById(id)) {
            throw new CinemaException(HttpStatus.BAD_REQUEST.value(), "Hall not found");
        }
        hallRepository.deleteById(id);
    }

}