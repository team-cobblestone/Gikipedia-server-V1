package team.cobblestone.gikipedia.server.v1.domain.document.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.Document;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.DocumentNamespace;

@Getter
@Builder
public class DocumentResponse {

    private Long id;

    private String title;

    private String slug;

    private DocumentNamespace namespace;

    private String content;

    private Integer currentVersion;

    private String authorUsername;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static DocumentResponse from(Document document) {
        return DocumentResponse.builder().id(document.getId()).title(document.getTitle()).slug(document.getSlug())
                .namespace(document.getNamespace())
                .content(document.getCurrentVersion() != null ? document.getCurrentVersion().getContent() : "")
                .currentVersion(
                        document.getCurrentVersion() != null ? document.getCurrentVersion().getVersionNumber() : 0)
                .authorUsername(document.getCurrentVersion() != null && document.getCurrentVersion().getAuthor() != null
                        ? document.getCurrentVersion().getAuthor().getUsername()
                        : null)
                .createdAt(document.getCreatedAt()).updatedAt(document.getUpdatedAt()).build();
    }

}
