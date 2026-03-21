package md.cineticket.cinemasystem.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDto {
    private Long id;
    private LocalDateTime bookingDate;
    private Double totalPrice;
    private Long userId;
    private Long screeningId;
    private List<Long> seatIds;
}