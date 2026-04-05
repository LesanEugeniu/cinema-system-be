package md.cineticket.cinemasystem.repo;

import md.cineticket.cinemasystem.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    @Query("""
                SELECT s FROM Screening s
                WHERE s.startTime < :end
                  AND s.endTime > :start
            """)
    List<Screening> findOverlappingScreenings(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
