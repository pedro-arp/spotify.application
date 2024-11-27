package jornada.dev.spotify.application.domain;

import lombok.Builder;

@Builder
public record SpotifyError(Integer status, String message) {


}
