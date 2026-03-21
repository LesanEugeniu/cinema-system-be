package md.cineticket.cinemasystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CinemaException extends RuntimeException {

    private int statusCode;

    private String errorType;

}
