package md.cineticket.cinemasystem.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.transaction.Transactional;
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
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
    private final DtoMapper dtoMapper;
    private final TicketPdfGenerator ticketPdfGenerator;

    public BookingDto create(BookingDto dto, String email) {
        Booking booking = dtoMapper.toEntity(dto);
        var user = userRepository.findByEmail(email)
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
        Booking booking = getBookingEntity(id);
        return dtoMapper.toDto(booking);
    }

    public BookingDto update(Long id, BookingDto dto) {
        Booking booking = getBookingEntity(id);

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

    public List<BookingDto> getBookingsByScreeningId(Long id) {
        return bookingRepository.findAllByScreeningId(id)
                .stream()
                .map(dtoMapper::toDto)
                .toList();
    }

    public Booking getBookingEntity(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new CinemaException(HttpStatus.BAD_REQUEST.value(), "Booking not found"));
    }

    public void generateTicketsPdf(Long bookingId, ServletOutputStream outputStream, String loggedUserEmail) {
        Booking booking = getBookingEntity(bookingId);
        if (!booking.getUser().getEmail().equals(loggedUserEmail)) {
            throw new CinemaException(HttpStatus.FORBIDDEN.value(), "No acces for this user");
        }
        ticketPdfGenerator.generateTicketsPdf(booking, outputStream);
    }

    public List<BookingDto> getBookingsByUser(String userEmail) {
        return bookingRepository.findAllByUser_Email(userEmail).stream().map(dtoMapper::toDto).toList();
    }

}