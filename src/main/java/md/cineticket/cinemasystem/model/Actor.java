package md.cineticket.cinemasystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "actors")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY)
    private List<Movie> movies = new ArrayList<>();
}