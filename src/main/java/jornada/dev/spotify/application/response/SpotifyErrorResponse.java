package jornada.dev.spotify.application.response;

import jornada.dev.spotify.application.domain.SpotifyError;
import lombok.Builder;

@Builder
public record SpotifyErrorResponse(SpotifyError error) {
}

