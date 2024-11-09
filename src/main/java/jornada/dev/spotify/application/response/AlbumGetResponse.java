package jornada.dev.spotify.application.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jornada.dev.spotify.application.domain.Artist;
import lombok.Builder;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record AlbumGetResponse(
        String albumType,
        int totalTracks,
        List<Artist> artists,
        String href,
        String id,
        String name,
        String releaseDate,
        String releaseDatePrecision,
        String type,
        String uri,
        String label,
        int popularity
) {
}



