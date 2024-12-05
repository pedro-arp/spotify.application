package jornada.dev.spotify.application.controller;

import jornada.dev.spotify.application.response.TokenResponse;
import jornada.dev.spotify.application.service.SpotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"v1/spotify-api"})
@RequiredArgsConstructor
@Log4j2
public class SpotifyApiController {

    private final SpotifyService spotifyService;


    @PostMapping("/token")
    public ResponseEntity<TokenResponse> token() {

        var token = spotifyService.token();

        return ResponseEntity.status(HttpStatus.CREATED).body(token);

    }

}
