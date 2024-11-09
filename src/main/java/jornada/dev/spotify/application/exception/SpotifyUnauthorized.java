package jornada.dev.spotify.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SpotifyUnauthorized extends ResponseStatusException {

    public SpotifyUnauthorized(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
