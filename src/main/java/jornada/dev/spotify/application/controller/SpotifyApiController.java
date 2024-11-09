package jornada.dev.spotify.application.controller;

import jornada.dev.spotify.application.response.AlbumGetResponse;
import jornada.dev.spotify.application.response.TokenResponse;
import jornada.dev.spotify.application.service.SpotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumGetResponse> getAlbum(@PathVariable String albumId) {

        var albumGetResponse = spotifyService.getAlbum(albumId);

        var artist = albumGetResponse.artists().getFirst().getName();

        log.info(artist);

        return ResponseEntity.ok(albumGetResponse);

    }



}
