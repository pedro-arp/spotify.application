package jornada.dev.spotify.application.exception;

import jornada.dev.spotify.application.response.ErrorTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandlerAdvice {


    @ExceptionHandler(SpotifyBadTokenInserted.class)
    public ResponseEntity<ErrorTokenResponse> handleBadTokenInsertedException(SpotifyBadTokenInserted ex) {
        var errorResponse = new ErrorTokenResponse(HttpStatus.BAD_REQUEST.value(), ex.getReason());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }



}
