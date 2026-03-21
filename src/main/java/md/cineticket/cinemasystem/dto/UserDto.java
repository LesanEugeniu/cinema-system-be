package md.cineticket.cinemasystem.dto;

import lombok.*;
import md.cineticket.cinemasystem.model.Role;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private List<Long> bookingIds;
}
