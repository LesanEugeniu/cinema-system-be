package md.cineticket.cinemasystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hall")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "total_rows", nullable = false)
    @EqualsAndHashCode.Include
    private Integer totalRows;

    @Column(name = "seats_per_row", nullable = false)
    @EqualsAndHashCode.Include
    private Integer seatsPerRow;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Screening> screenings = new ArrayList<>();
}