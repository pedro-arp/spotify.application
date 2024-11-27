package jornada.dev.spotify.application.commons;

import jornada.dev.spotify.application.domain.SpotifyError;
import jornada.dev.spotify.application.response.AlbumGetResponse;
import jornada.dev.spotify.application.response.SpotifyErrorResponse;
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
                .error("invalid_client")
                .error_description(errorDescription)
                .build();
    }

    public AlbumGetResponse newAlbumGetResponse() {
        return AlbumGetResponse.builder()
                .albumType("album")
                .id("4aawyAB9vmqN3uQ7FjRGTy")
                .name("Global Warming")
                .href("https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy")
                .label("Mr.305/Polo Grounds Music/RCA Records")
                .popularity(56)
                .releaseDate("2012-11-16")
                .uri("spotify:album:4aawyAB9vmqN3uQ7FjRGTy")
                .releaseDatePrecision("day")
                .totalTracks(16)
                .type("track")
                .build();
    }

    public SpotifyErrorResponse newGetBadRequestErrorResponse(SpotifyError error) {

        return SpotifyErrorResponse.builder()
                .error(error).build();

    }

    public SpotifyError newSpotifyError(int status, String message) {
        return SpotifyError.builder()
                .status(status)
                .message(message).build();


    }

}
