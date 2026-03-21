package md.cineticket.cinemasystem.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActorDto {
    private Long id;
    private String name;
    private List<Long> movieIds;
}