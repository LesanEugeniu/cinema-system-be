package md.cineticket.cinemasystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
public class ErrorDetail {

    private int statusCode;

    private String errorType;

    private String message;

    private Instant timestamp;

    private String path;

    private String correlationId;

}
