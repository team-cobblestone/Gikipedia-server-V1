package team.cobblestone.gikipedia.server.v1.domain.document.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.DocumentNamespace;

@Getter
@NoArgsConstructor
public class DocumentRequest {

    @NotBlank(message = "제목은 필수입니다")
    @Size(max = 255, message = "제목은 255자를 초과할 수 없습니다")
    private String title;

    @NotBlank(message = "슬러그는 필수입니다")
    @Size(max = 255, message = "슬러그는 255자를 초과할 수 없습니다")
    private String slug;

    @NotBlank(message = "내용은 필수입니다")
    private String content;

    @Size(max = 500, message = "변경 요약은 500자를 초과할 수 없습니다")
    private String summary;

    private DocumentNamespace namespace;

    public DocumentRequest(String title, String slug, String content, String summary, DocumentNamespace namespace) {
        this.title = title;
        this.slug = slug;
        this.content = content;
        this.summary = summary;
        this.namespace = namespace;
    }

}
