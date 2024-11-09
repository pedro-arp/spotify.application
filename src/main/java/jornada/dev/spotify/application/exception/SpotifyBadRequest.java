package jornada.dev.spotify.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SpotifyBadRequest extends ResponseStatusException {

    public SpotifyBadRequest(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
