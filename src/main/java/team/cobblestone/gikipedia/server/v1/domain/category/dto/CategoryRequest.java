package team.cobblestone.gikipedia.server.v1.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "카테고리 이름은 필수입니다")
    @Size(max = 100, message = "카테고리 이름은 100자를 초과할 수 없습니다")
    private String name;

    @Size(max = 500, message = "설명은 500자를 초과할 수 없습니다")
    private String description;

    private Long parentId;

    public CategoryRequest(String name, String description, Long parentId) {
        this.name = name;
        this.description = description;
        this.parentId = parentId;
    }

}
