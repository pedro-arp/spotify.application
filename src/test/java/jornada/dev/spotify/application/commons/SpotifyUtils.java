package jornada.dev.spotify.application.commons;

import jornada.dev.spotify.application.response.TokenErrorResponse;
import jornada.dev.spotify.application.response.TokenResponse;
import org.springframework.stereotype.Component;


@Component
public class SpotifyUtils {

    public TokenResponse newPostTokenResponse() {
        return TokenResponse.builder()
                .accessToken("FJI120J341I2DJ1ÇKLAMKDS1289")
                .tokenType("Bearer")
                .expiresIn("3600")
                .build();
    }

    public TokenErrorResponse newPostErrorTokenResponse(String errorDescription) {
        return TokenErrorResponse.builder()
                .status(400)
                .error_description(errorDescription)
                .build();
    }


}
