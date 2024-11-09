package jornada.dev.spotify.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SpotifyNotFound extends ResponseStatusException {

    public SpotifyNotFound(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
