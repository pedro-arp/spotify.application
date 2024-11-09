package jornada.dev.spotify.application.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrackItem {
    private List<Artist> artists;
    private List<String> availableMarkets;
    private int discNumber;
    private int durationMs;
    private boolean explicit;
    private String externalSpotifyUrl;
    private String href;
    private String id;
    private boolean isPlayable;
    private LinkedFrom linkedFrom;
    private String restrictionReason;
    private String name;
    private String previewUrl;
    private int trackNumber;
    private String type;
    private String uri;
    private boolean isLocal;

}
