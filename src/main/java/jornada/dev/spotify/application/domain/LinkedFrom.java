package jornada.dev.spotify.application.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkedFrom {
    private String externalSpotifyUrl;
    private String href;
    private String id;
    private String type;
    private String uri;

}