package jornada.dev.spotify.application.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalIds {
    private String isrc;
    private String ean;
    private String upc;

}