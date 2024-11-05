package jornada.dev.spotify.application.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix = "spotify-api.token")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SpotifyApiConfigurationPropertiesToken(String tokenBaseUrl, String uriToken, String grantType,
                                                     String clientId,
                                                     String clientSecret) {
}

