package md.cineticket.cinemasystem.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HallDto {
    private Long id;
    private String name;
    private Integer totalRows;
    private Integer seatsPerRow;
    private List<Long> seatIds;
    private List<Long> screeningIds;
}