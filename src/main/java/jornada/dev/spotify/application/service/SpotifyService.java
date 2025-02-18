package jornada.dev.spotify.application.service;

import jornada.dev.spotify.application.config.SpotifyApiConfigurationPropertiesToken;
import jornada.dev.spotify.application.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotifyService {

    private final RestClient spotifyRestClient;

    private final SpotifyApiConfigurationPropertiesToken tokenProperties;


    public MultiValueMap<String, String> httpBody() {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "client_credentials");
        params.add("client_id", tokenProperties.clientId());
        params.add("client_secret", tokenProperties.clientSecret());

        return new HttpEntity<>(params).getHeaders();

    }

    public TokenResponse token() {

        return spotifyRestClient.post()
                .uri(tokenProperties.tokenBaseUrl() + tokenProperties.uriToken())
                .headers(httpHeaders -> httpHeaders.add("Content-Type", "application/x-www-form-urlencoded"))
                .body(httpBody())
                .retrieve()
                .toEntity(TokenResponse.class).getBody();
    }


}