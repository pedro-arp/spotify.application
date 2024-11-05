package jornada.dev.spotify.application.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spotify-api")
public record SpotifyApiConfigurationProperties(String baseUrl, String uriAlbums) {
}



