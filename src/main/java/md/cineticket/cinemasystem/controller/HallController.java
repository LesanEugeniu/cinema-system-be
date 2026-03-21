package md.cineticket.cinemasystem.controller;

import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.HallDto;
import md.cineticket.cinemasystem.service.HallService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/halls")
@RequiredArgsConstructor
public class HallController {

    private final HallService hallService;

    @PostMapping
    public ResponseEntity<HallDto> create(@RequestBody HallDto dto) {
        HallDto created = hallService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<HallDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<HallDto> halls = hallService.getAll(pageable);
        return ResponseEntity.ok(halls);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HallDto> getById(@PathVariable Long id) {
        HallDto hall = hallService.getById(id);
        return ResponseEntity.ok(hall);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HallDto> update(@PathVariable Long id, @RequestBody HallDto dto) {
        HallDto updated = hallService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        hallService.delete(id);
        return ResponseEntity.noContent().build();
    }

}