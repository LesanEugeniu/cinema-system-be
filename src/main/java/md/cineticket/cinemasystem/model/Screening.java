package md.cineticket.cinemasystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "screenings")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private LocalDateTime startTime;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "hall_id", nullable = false)
    @EqualsAndHashCode.Include
    private Hall hall;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    @EqualsAndHashCode.Include
    private Movie movie;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Booking> bookings = new ArrayList<>();

}