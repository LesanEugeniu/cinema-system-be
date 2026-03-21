package md.cineticket.cinemasystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatDto {
    private Long id;
    private Integer rowNumber;
    private Integer seatNumber;
    private Long hallId;
}