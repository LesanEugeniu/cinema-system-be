package md.cineticket.cinemasystem.specification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import md.cineticket.cinemasystem.dto.MovieSearchDto;
import md.cineticket.cinemasystem.model.Actor;
import md.cineticket.cinemasystem.model.Director;
import md.cineticket.cinemasystem.model.Movie;
import org.springframework.data.jpa.domain.Specification;


public class MovieSpecification {

    public static Specification<Movie> search(MovieSearchDto dto) {
        return (root, query, cb) -> {
            query.distinct(true); // avoid duplicates due to joins

            Predicate predicate = cb.conjunction();

            // Filter by actors
            if (dto.getActorIds() != null && !dto.getActorIds().isEmpty()) {
                Join<Movie, Actor> actorJoin = root.join("actors", JoinType.INNER);
                predicate = cb.and(predicate, actorJoin.get("id").in(dto.getActorIds()));
            }

            // Filter by directors
            if (dto.getDirectorIds() != null && !dto.getDirectorIds().isEmpty()) {
                Join<Movie, Director> directorJoin = root.join("directors", JoinType.INNER);
                predicate = cb.and(predicate, directorJoin.get("id").in(dto.getDirectorIds()));
            }

            // Filter by SoundFormat
            if (dto.getSoundFormat() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("soundFormat"), dto.getSoundFormat()));
            }

            // Filter by FormatType
            if (dto.getFormatType() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("formatType"), dto.getFormatType()));
            }

            // Filter by AudioLanguage
            if (dto.getAudioLanguage() != null) {
                predicate = cb.and(predicate, cb.isMember(dto.getAudioLanguage(), root.get("audioLanguages")));
            }

            if (dto.getGenre() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("genre"), dto.getGenre()));
            }

            return predicate;
        };
    }
}