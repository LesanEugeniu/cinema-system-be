package md.cineticket.cinemasystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "seat",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"hall_id", "row_number", "seat_number"}
        ))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_number", nullable = false)
    private Integer rowNumber;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;
}