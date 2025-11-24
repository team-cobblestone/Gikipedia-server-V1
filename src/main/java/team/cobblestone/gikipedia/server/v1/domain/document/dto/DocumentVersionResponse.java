package team.cobblestone.gikipedia.server.v1.domain.document.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.DocumentVersion;

@Getter
@Builder
public class DocumentVersionResponse {

    private Long id;

    private Integer versionNumber;

    private String content;

    private String summary;

    private String authorUsername;

    private LocalDateTime createdAt;

    public static DocumentVersionResponse from(DocumentVersion version) {
        return DocumentVersionResponse.builder().id(version.getId()).versionNumber(version.getVersionNumber())
                .content(version.getContent()).summary(version.getSummary())
                .authorUsername(version.getAuthor() != null ? version.getAuthor().getUsername() : null)
                .createdAt(version.getCreatedAt()).build();
    }

}
