package jornada.dev.spotify.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandlerAdvice {

    @ExceptionHandler(SpotifyBadRequest.class)
    public ResponseEntity<DefaultErrorMessage> handleNotFoundException(SpotifyBadRequest ex) {
        var errorResponse = new DefaultErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getReason());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(SpotifyUnauthorized.class)
    public ResponseEntity<DefaultErrorMessage> handleNotFoundException(SpotifyUnauthorized ex) {
        var errorResponse = new DefaultErrorMessage(HttpStatus.UNAUTHORIZED.value(), ex.getReason());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(SpotifyNotFound.class)
    public ResponseEntity<DefaultErrorMessage> handleNotFoundException(SpotifyNotFound ex) {
        var errorResponse = new DefaultErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getReason());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


}
