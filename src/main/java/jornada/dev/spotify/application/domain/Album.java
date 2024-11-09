package jornada.dev.spotify.application.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter

public class Album {
    private String albumType;
    private int totalTracks;
    private List<String> availableMarkets;
    private String externalSpotifyUrl;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private String releaseDate;
    private String releaseDatePrecision;
    private String restrictionReason;
    private String type;
    private String uri;
    private List<Artist> artists;
    private Tracks tracks;
    private List<Copyright> copyrights;
    private ExternalIds externalIds;
    private List<String> genres;
    private String label;
    private int popularity;

}
