package md.cineticket.cinemasystem.controller;

import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.SeatDto;
import md.cineticket.cinemasystem.service.SeatService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<SeatDto> create(@RequestBody SeatDto dto) {
        SeatDto created = seatService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SeatDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<SeatDto> seats = seatService.getAll(pageable);
        return ResponseEntity.ok(seats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatDto> getById(@PathVariable Long id) {
        SeatDto seat = seatService.getById(id);
        return ResponseEntity.ok(seat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatDto> update(@PathVariable Long id, @RequestBody SeatDto dto) {
        SeatDto updated = seatService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seatService.delete(id);
        return ResponseEntity.noContent().build();
    }

}