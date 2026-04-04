package md.cineticket.cinemasystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.DtoMapper;
import md.cineticket.cinemasystem.dto.HallDto;
import md.cineticket.cinemasystem.exception.CinemaException;
import md.cineticket.cinemasystem.model.Hall;
import md.cineticket.cinemasystem.repo.HallRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HallService {

    private final HallRepository hallRepository;
    private final DtoMapper dtoMapper;

    public HallDto create(HallDto dto) {
        Hall hall = dtoMapper.toEntity(dto);
        Hall saved = hallRepository.save(hall);
        return dtoMapper.toDto(saved);
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

        hall.setName(dto.getName());
        hall.setTotalRows(dto.getTotalRows());
        hall.setSeatsPerRow(dto.getSeatsPerRow());

        Hall updated = hallRepository.save(hall);
        return dtoMapper.toDto(updated);
    }

    public void delete(Long id) {
        if (!hallRepository.existsById(id)) {
            throw new CinemaException(HttpStatus.BAD_REQUEST.value(), "Hall not found");
        }
        hallRepository.deleteById(id);
    }

}