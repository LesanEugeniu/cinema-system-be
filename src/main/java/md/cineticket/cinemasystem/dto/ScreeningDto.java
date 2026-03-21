package md.cineticket.cinemasystem.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreeningDto {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long hallId;
    private Long movieId;
    private List<Long> bookingIds = new ArrayList<>();
}