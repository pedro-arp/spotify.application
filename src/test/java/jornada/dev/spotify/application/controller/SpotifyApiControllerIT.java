package jornada.dev.spotify.application.controller;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import jornada.dev.spotify.application.commons.FileUtils;
import jornada.dev.spotify.application.config.IntegrationTestContainers;
import jornada.dev.spotify.application.config.RestAssuredConfig;
import jornada.dev.spotify.application.config.SpotifyApiConfigurationPropertiesToken;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestAssuredConfig.class)
@AutoConfigureWireMock(port = 0, files = "classpath:/wiremock/spotify-api", stubs = "classpath:/wiremock/spotify-api/mappings")
class SpotifyApiControllerIT extends IntegrationTestContainers {

    private static final String URL = "v1/spotify-api";

    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private SpotifyApiConfigurationPropertiesToken tokenProperties;

    @Autowired
    @Qualifier(value = "restAssuredConfiguration")
    private RequestSpecification restAssuredConfiguration;

    @BeforeEach
    void setUrl() {
        RestAssured.requestSpecification = restAssuredConfiguration;
    }


    public MultiValueMap<String, String> httpBody(String clientId, String clientSecret) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "client_credentials");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);

        return new HttpEntity<>(params).getHeaders();

    }

    @Test
    @DisplayName("token() returns SpotifyApiAccessTokenResponse")
    void token_Returns_TokenResponse_WhenSuccessful() throws Exception {

        var expectedResponse = fileUtils.readResourceFile("spotify-api/expected-post-token-response-201.json");

        RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .log().all()
                .body(httpBody(tokenProperties.clientId(), tokenProperties.clientSecret()))
                .when()
                .post(URL + "/token")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body(Matchers.equalTo(expectedResponse));
    }


    @Test
    @DisplayName("token() returns TokenErrorResponse when Client is invalid")
    void token_ReturnsTokenErrorResponse_WhenClientInvalid() throws Exception {

        var invalidClient = "INVALID_CLIENT";

        var expectedResponse = fileUtils.readResourceFile("spotify-api/expected-post-invalid-client-response-400.json");

        RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .log().all()
                .with()
                .body(httpBody(invalidClient, invalidClient))
                .when()
                .post(URL + "/token")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(Matchers.equalTo(expectedResponse));
    }

}
