package md.cineticket.cinemasystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "directors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String name;

    @ManyToMany(mappedBy = "directors", fetch = FetchType.LAZY)
    private List<Movie> movies = new ArrayList<>();
}
