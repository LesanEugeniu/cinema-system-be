package md.cineticket.cinemasystem.repo;

import md.cineticket.cinemasystem.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    List<Screening> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}
