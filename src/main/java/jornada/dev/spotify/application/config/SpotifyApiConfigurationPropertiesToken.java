package jornada.dev.spotify.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spotify-api.token")
public record SpotifyApiConfigurationPropertiesToken(String tokenBaseUrl, String uriToken, String grantType,
                                                     String clientId,
                                                     String clientSecret) {
}

