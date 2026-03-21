package md.cineticket.cinemasystem.controller;

import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.dto.MovieDto;
import md.cineticket.cinemasystem.service.MovieService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<MovieDto> create(
            @RequestPart("movie") MovieDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        dto.setImagePath(movieService.saveFile(image));
        MovieDto created = movieService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<MovieDto> update(
            @PathVariable Long id,
            @RequestPart("movie") MovieDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {
        dto.setImagePath(movieService.saveFile(image));
        MovieDto updated = movieService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<MovieDto> movies = movieService.getAll(pageable);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getById(@PathVariable Long id) {
        MovieDto movie = movieService.getById(id);
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }

}