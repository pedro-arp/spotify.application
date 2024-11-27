package jornada.dev.spotify.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jornada.dev.spotify.application.commons.SpotifyUtils;
import jornada.dev.spotify.application.config.RestClientConfiguration;
import jornada.dev.spotify.application.config.SpotifyApiConfigurationProperties;
import jornada.dev.spotify.application.config.SpotifyApiConfigurationPropertiesToken;
import jornada.dev.spotify.application.exception.SpotifyBadRequest;
import jornada.dev.spotify.application.exception.SpotifyNotFound;
import jornada.dev.spotify.application.exception.SpotifyUnauthorized;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;


@RestClientTest({SpotifyService.class, RestClientConfiguration.class, SpotifyApiConfigurationProperties.class, SpotifyUtils.class, ObjectMapper.class, SpotifyApiConfigurationPropertiesToken.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpotifyServiceTest {

    @Autowired
    private SpotifyService service;
    @Autowired
    private SpotifyApiConfigurationPropertiesToken tokenProperties;
    @Autowired
    private SpotifyApiConfigurationProperties properties;
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
    @Order(1)
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
            "'Invalid client id', '400 BAD_REQUEST \"TokenErrorResponse[error=invalid_client, error_description=Invalid client id]\"'",
            "'Invalid client secret', '400 BAD_REQUEST \"TokenErrorResponse[error=invalid_client, error_description=Invalid client secret]\"'",
            "'Invalid client', '400 BAD_REQUEST \"TokenErrorResponse[error=invalid_client, error_description=Invalid client]\"'"
    })

    @DisplayName("token() - Tests different authentication errors")
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
                .isInstanceOf(SpotifyBadRequest.class);
    }

    @Test
    @DisplayName("getAlbum() returns AlbumGetResponse when Successful")
    @Order(3)
    void getAlbum_ReturnsAlbumGetResponse_WhenSuccessful() throws JsonProcessingException {

        var albumId = "4aawyAB9vmqN3uQ7FjRGTy";

        var albumGetResponse = spotifyUtils.newAlbumGetResponse();

        var jsonResponse = mapper.writeValueAsString(albumGetResponse);

        var requestTo = MockRestRequestMatchers.requestToUriTemplate(properties.baseUrl() + properties.uriAlbums(), albumId);

        var withSuccess = MockRestResponseCreators.withSuccess(jsonResponse, MediaType.APPLICATION_JSON);

        server.expect(requestTo).andRespond(withSuccess);

        Assertions.assertThat(service.getAlbum(albumId)).isNotNull().isEqualTo(albumGetResponse);

    }


    @Test
    @DisplayName("getAlbum() returns SpotifyBadRequest when id Incorrect")
    @Order(4)
    void getAlbum_ReturnsSpotifyBadRequest_WhenAlbumIdIncorrect() throws JsonProcessingException {

        var albumId = "BadRequestId";

        var albumGetErrorResponse = spotifyUtils.newGetBadRequestErrorResponse(spotifyUtils.newSpotifyError(HttpStatus.BAD_REQUEST.value(),"Invalid base62 id"));

        var jsonResponse = mapper.writeValueAsString(albumGetErrorResponse);

        var expectedErrorMessage = """
                400 BAD_REQUEST "SpotifyErrorResponse[error=SpotifyError[status=400, message=Invalid base62 id]]"
                """.trim();

        var requestTo = MockRestRequestMatchers.requestToUriTemplate(properties.baseUrl() + properties.uriAlbums(), albumId);

        var withResourceBadRequest = MockRestResponseCreators.withBadRequest().body(jsonResponse);

        server.expect(requestTo).andRespond(withResourceBadRequest);

        Assertions.assertThatException()
                .isThrownBy(() -> service.getAlbum(albumId))
                .withMessage(expectedErrorMessage)
                .isInstanceOf(SpotifyBadRequest.class);
    }

    @Test
    @DisplayName("getAlbum() returns SpotifyBadRequest when Bearer Token not Provided")
    @Order(5)
    void getAlbum_ReturnsSpotifyBadRequest_WhenBearerTokenNotProvided() throws JsonProcessingException {

        var albumId = "BadRequestId";

        var albumGetErrorResponse = spotifyUtils.newGetBadRequestErrorResponse(spotifyUtils.newSpotifyError(HttpStatus.BAD_REQUEST.value(),"Only valid bearer authentication supported"));

        var jsonResponse = mapper.writeValueAsString(albumGetErrorResponse);

        var expectedErrorMessage = """
                400 BAD_REQUEST "SpotifyErrorResponse[error=SpotifyError[status=400, message=Only valid bearer authentication supported]]"
                """.trim();

        var requestTo = MockRestRequestMatchers.requestToUriTemplate(properties.baseUrl() + properties.uriAlbums(), albumId);

        var withResourceBadRequest = MockRestResponseCreators.withBadRequest().body(jsonResponse);

        server.expect(requestTo).andRespond(withResourceBadRequest);

        Assertions.assertThatException()
                .isThrownBy(() -> service.getAlbum(albumId))
                .withMessage(expectedErrorMessage)
                .isInstanceOf(SpotifyBadRequest.class);
    }

    @Test
    @DisplayName("getAlbum() returns SpotifyNotFound when id NotFound")
    @Order(6)
    void getAlbum_ReturnsSpotifyNotFound_WhenAlbumIdNotFound() throws JsonProcessingException {

        var albumId = "NotFoundId";

        var albumGetErrorResponse = spotifyUtils.newGetBadRequestErrorResponse(spotifyUtils.newSpotifyError(HttpStatus.NOT_FOUND.value(),"Resource not found"));

        var jsonResponse = mapper.writeValueAsString(albumGetErrorResponse);

        var expectedErrorMessage = """
                404 NOT_FOUND "SpotifyErrorResponse[error=SpotifyError[status=404, message=Resource not found]]"
                """.trim();

        var requestTo = MockRestRequestMatchers.requestToUriTemplate(properties.baseUrl() + properties.uriAlbums(), albumId);

        var withResourceNotFound = MockRestResponseCreators.withResourceNotFound().body(jsonResponse);

        server.expect(requestTo).andRespond(withResourceNotFound);

        Assertions.assertThatException()
                .isThrownBy(() -> service.getAlbum(albumId))
                .withMessage(expectedErrorMessage)
                .isInstanceOf(SpotifyNotFound.class);
    }

    @Test
    @DisplayName("getAlbum() returns SpotifyUnauthorized when header is not provided")
    @Order(7)
    void getAlbum_ReturnsSpotifyUnauthorized_WhenHeaderIsNotProvided() throws JsonProcessingException {

        var albumId = "4aawyAB9vmqN3uQ7FjRGTy";

        var albumGetErrorResponse = spotifyUtils.newGetBadRequestErrorResponse(spotifyUtils.newSpotifyError(HttpStatus.UNAUTHORIZED.value(),"No token provided"));

        var jsonResponse = mapper.writeValueAsString(albumGetErrorResponse);

        var expectedErrorMessage = """
                401 UNAUTHORIZED "SpotifyErrorResponse[error=SpotifyError[status=401, message=No token provided]]"
                """.trim();

        var requestTo = MockRestRequestMatchers.requestToUriTemplate(properties.baseUrl() + properties.uriAlbums(), albumId);

        var withResourceUnauthorized = MockRestResponseCreators.withUnauthorizedRequest().body(jsonResponse);

        server.expect(requestTo).andRespond(withResourceUnauthorized);

        Assertions.assertThatException()
                .isThrownBy(() -> service.getAlbum(albumId))
                .withMessage(expectedErrorMessage)
                .isInstanceOf(SpotifyUnauthorized.class);
    }

}


