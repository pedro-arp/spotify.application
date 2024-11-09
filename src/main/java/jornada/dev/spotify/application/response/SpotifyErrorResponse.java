package jornada.dev.spotify.application.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jornada.dev.spotify.application.domain.SpotifyError;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SpotifyErrorResponse(SpotifyError error) {
}

