package jornada.dev.spotify.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jornada.dev.spotify.application.config.SpotifyApiConfigurationProperties;
import jornada.dev.spotify.application.config.SpotifyApiConfigurationPropertiesToken;
import jornada.dev.spotify.application.exception.SpotifyBadRequest;
import jornada.dev.spotify.application.exception.SpotifyNotFound;
import jornada.dev.spotify.application.exception.SpotifyUnauthorized;
import jornada.dev.spotify.application.response.AlbumGetResponse;
import jornada.dev.spotify.application.response.SpotifyErrorResponse;
import jornada.dev.spotify.application.response.TokenErrorResponse;
import jornada.dev.spotify.application.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotifyService {

    @Value("${variables.bearer-token}")
    private String bearerToken;

    private final RestClient spotifyRestClient;

    private final SpotifyApiConfigurationPropertiesToken tokenProperties;

    private final SpotifyApiConfigurationProperties properties;

    private final ObjectMapper mapper;

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
                .body(httpBody())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    var tokenErrorResponse = mapper.readValue(response.getBody().readAllBytes(), TokenErrorResponse.class);
                    throw new SpotifyBadRequest(tokenErrorResponse.toString());
                }))
                .toEntity(TokenResponse.class).getBody();
    }

    public AlbumGetResponse getAlbum(String albumId) {
        return spotifyRestClient
                .get()
                .uri(properties.baseUrl() + properties.uriAlbums(), albumId)
                .header("Authorization", "Bearer " + bearerToken)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    var albumErrorResponse = mapper.readValue(response.getBody().readAllBytes(), SpotifyErrorResponse.class);
                    switch (response.getStatusCode()) {
                        case HttpStatus.BAD_REQUEST:
                            throw new SpotifyBadRequest(albumErrorResponse.toString());
                        case HttpStatus.UNAUTHORIZED:
                            throw new SpotifyUnauthorized(albumErrorResponse.toString());
                        case HttpStatus.NOT_FOUND:
                            throw new SpotifyNotFound(albumErrorResponse.toString());
                        default:
                            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, albumErrorResponse.toString());
                    }
                })
                .body(AlbumGetResponse.class);
    }

}