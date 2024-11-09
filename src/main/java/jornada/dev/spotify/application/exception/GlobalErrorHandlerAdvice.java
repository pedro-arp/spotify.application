package jornada.dev.spotify.application.exception;

import jornada.dev.spotify.application.response.TokenErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandlerAdvice {


    @ExceptionHandler(SpotifyBadTokenInserted.class)
    public ResponseEntity<TokenErrorResponse> handleBadTokenInsertedException(SpotifyBadTokenInserted ex) {
        var errorResponse = new TokenErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getReason());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }



}
