package jornada.dev.spotify.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jornada.dev.spotify.application.commons.SpotifyUtils;
import jornada.dev.spotify.application.config.RestClientConfiguration;
import jornada.dev.spotify.application.config.SpotifyApiConfigurationProperties;
import jornada.dev.spotify.application.config.SpotifyApiConfigurationPropertiesToken;
import jornada.dev.spotify.application.exception.SpotifyBadTokenInserted;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;


@RestClientTest({SpotifyService.class, RestClientConfiguration.class, SpotifyUtils.class, ObjectMapper.class, SpotifyApiConfigurationProperties.class, SpotifyApiConfigurationPropertiesToken.class})
class SpotifyServiceTest {

    @Autowired
    private SpotifyService service;
    @Autowired
    private SpotifyApiConfigurationPropertiesToken tokenProperties;
    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private SpotifyUtils spotifyUtils;
    @Autowired
    private ObjectMapper mapper;

    @AfterEach
    void reset() {
        server.reset();
    }

    @Test
    @DisplayName("token() Returns Bearer Token when Successful")
    void token_ReturnsSpotifyApiAccessTokenResponse_WhenSuccessful() throws JsonProcessingException {

        var tokenResponse = spotifyUtils.newPostTokenResponse();

        var jsonResponse = mapper.writeValueAsString(tokenResponse);

        var requestTo = MockRestRequestMatchers.requestToUriTemplate(tokenProperties.tokenBaseUrl() + tokenProperties.uriToken());

        var withSuccess = MockRestResponseCreators.withSuccess(jsonResponse, MediaType.APPLICATION_JSON);

        server.expect(requestTo).andRespond(withSuccess);

        Assertions.assertThat(service.token())
                .isNotNull()
                .isEqualTo(tokenResponse);

    }


    @ParameterizedTest
    @CsvSource({
            "Invalid client id, 400 BAD_REQUEST \"Invalid client id\"",
            "Invalid client secret, 400 BAD_REQUEST \"Invalid client secret\"",
            "Invalid client, 400 BAD_REQUEST \"Invalid client\""
    })
    @DisplayName("token() - Testa diferentes erros de autenticação")
    void token_bad_request(String errorDescription, String expectedErrorMessage) throws JsonProcessingException {

        var errorTokenResponse = spotifyUtils.newPostErrorTokenResponse(errorDescription);

        var jsonResponse = mapper.writeValueAsString(errorTokenResponse);

        var requestTo = MockRestRequestMatchers.requestToUriTemplate(
                tokenProperties.tokenBaseUrl() + tokenProperties.uriToken()
        );

        var withResourceBadRequest = MockRestResponseCreators.withBadRequest().body(jsonResponse);

        server.expect(requestTo).andRespond(withResourceBadRequest);

        Assertions.assertThatException()
                .isThrownBy(() -> service.token())
                .withMessage(expectedErrorMessage)
                .isInstanceOf(SpotifyBadTokenInserted.class);
    }


}