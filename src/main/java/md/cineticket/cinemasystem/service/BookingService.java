package md.cineticket.cinemasystem.service;

import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.BookingDto;
import md.cineticket.cinemasystem.dto.DtoMapper;
import md.cineticket.cinemasystem.exception.CinemaException;
import md.cineticket.cinemasystem.model.Booking;
import md.cineticket.cinemasystem.repo.BookingRepository;
import md.cineticket.cinemasystem.repo.ScreeningRepository;
import md.cineticket.cinemasystem.repo.UserRepository;
import md.cineticket.cinemasystem.security.SeatRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
    private final DtoMapper dtoMapper;

    public BookingDto create(BookingDto dto) {
        Booking booking = dtoMapper.toEntity(dto);

        var user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "User not found"));
        booking.setUser(user);

        var screening = screeningRepository.findById(dto.getScreeningId())
                .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "Screening not found"));
        booking.setScreening(screening);

        if (dto.getSeatIds() != null && !dto.getSeatIds().isEmpty()) {
            var seats = seatRepository.findAllById(dto.getSeatIds());
            if (seats.size() != dto.getSeatIds().size()) {
                throw new CinemaException(HttpStatus.BAD_REQUEST.value(), "Some seats not found");
            }
            booking.setSeats(seats);
        }

        Booking saved = bookingRepository.save(booking);
        return dtoMapper.toDto(saved);
    }

    public List<BookingDto> getAll(Pageable pageable) {
        return bookingRepository.findAll(pageable)
                .stream()
                .map(dtoMapper::toDto)
                .toList();
    }

    public BookingDto getById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "Booking not found"));
        return dtoMapper.toDto(booking);
    }

    public BookingDto update(Long id, BookingDto dto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "Booking not found"));

        if (dto.getUserId() != null) {
            var user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "User not found"));
            booking.setUser(user);
        }

        if (dto.getScreeningId() != null) {
            var screening = screeningRepository.findById(dto.getScreeningId())
                    .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "Screening not found"));
            booking.setScreening(screening);
        }

        if (dto.getSeatIds() != null && !dto.getSeatIds().isEmpty()) {
            var seats = seatRepository.findAllById(dto.getSeatIds());
            if (seats.size() != dto.getSeatIds().size()) {
                throw new CinemaException(HttpStatus.BAD_REQUEST.value(), "Some seats not found");
            }
            booking.setSeats(seats);
        }

        booking.setBookingDate(dto.getBookingDate());
        booking.setTotalPrice(dto.getTotalPrice());

        Booking updated = bookingRepository.save(booking);
        return dtoMapper.toDto(updated);
    }

    public void delete(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new CinemaException(HttpStatus.BAD_REQUEST.value(), "Booking not found");
        }
        bookingRepository.deleteById(id);
    }
}