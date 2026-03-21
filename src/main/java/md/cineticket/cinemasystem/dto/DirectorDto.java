package md.cineticket.cinemasystem.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectorDto {
    private Long id;
    private String name;
    private List<Long> movieIds;
}