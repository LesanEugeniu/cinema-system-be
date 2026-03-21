package md.cineticket.cinemasystem.controller;

import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.ScreeningDto;
import md.cineticket.cinemasystem.service.ScreeningService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/screenings")
@RequiredArgsConstructor
public class ScreeningController {

    private final ScreeningService screeningService;

    @PostMapping
    public ResponseEntity<ScreeningDto> create(@RequestBody ScreeningDto dto) {
        ScreeningDto created = screeningService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScreeningDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<ScreeningDto> screenings = screeningService.getAll(pageable);
        return ResponseEntity.ok(screenings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreeningDto> getById(@PathVariable Long id) {
        ScreeningDto screening = screeningService.getById(id);
        return ResponseEntity.ok(screening);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScreeningDto> update(@PathVariable Long id, @RequestBody ScreeningDto dto) {
        ScreeningDto updated = screeningService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        screeningService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}