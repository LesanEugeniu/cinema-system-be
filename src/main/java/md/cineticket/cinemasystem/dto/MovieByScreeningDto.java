package md.cineticket.cinemasystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovieByScreeningDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}