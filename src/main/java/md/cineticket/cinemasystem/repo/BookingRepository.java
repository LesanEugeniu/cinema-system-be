package md.cineticket.cinemasystem.repo;

import io.micrometer.observation.ObservationFilter;
import md.cineticket.cinemasystem.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.screening.id = :id")
    List<Booking> findAllByScreeningId(@Param("id") Long id);

    Page<Booking> findAllByUser_Email(String email, Pageable pageable);
}
