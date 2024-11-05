package jornada.dev.spotify.application.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfiguration {
    private final SpotifyApiConfigurationProperties spotifyApiConfigurationProperties;

    @Bean
    public RestClient spotifyApiClient(RestClient.Builder builder) {
        return builder.baseUrl(spotifyApiConfigurationProperties.baseUrl()).build();
    }

}

