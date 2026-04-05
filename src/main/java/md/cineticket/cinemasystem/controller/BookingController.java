package md.cineticket.cinemasystem.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.BookingDto;
import md.cineticket.cinemasystem.service.BookingService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDto> create(@RequestBody BookingDto dto) {
        BookingDto created = bookingService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<BookingDto> bookings = bookingService.getAll(pageable);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getById(@PathVariable Long id) {
        BookingDto booking = bookingService.getById(id);
        return ResponseEntity.ok(booking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> update(@PathVariable Long id, @RequestBody BookingDto dto) {
        BookingDto updated = bookingService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-screening/{id}")
    public ResponseEntity<List<BookingDto>> getBookingsByScreeningId(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingsByScreeningId(id));
    }

    @GetMapping("/{bookingId}/tickets/pdf")
    public void downloadTicketsPdf(@PathVariable Long bookingId,
                                   HttpServletResponse response,
                                   Principal principal
    ) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                    "attachment; filename=tickets_" + bookingId + ".pdf");

            bookingService.generateTicketsPdf(bookingId, response.getOutputStream(), principal.getName());
        } catch (Exception e) {
            throw new RuntimeException("Eroare la generarea PDF: " + e.getMessage(), e);
        }
    }

}