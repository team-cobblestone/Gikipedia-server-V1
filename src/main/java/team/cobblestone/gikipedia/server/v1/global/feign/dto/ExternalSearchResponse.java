package team.cobblestone.gikipedia.server.v1.global.feign.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExternalSearchResponse {

    private List<SearchResult> results;

    private Integer totalCount;

    @Getter
    @NoArgsConstructor
    public static class SearchResult {

        private String title;

        private String url;

        private String snippet;

    }

}
