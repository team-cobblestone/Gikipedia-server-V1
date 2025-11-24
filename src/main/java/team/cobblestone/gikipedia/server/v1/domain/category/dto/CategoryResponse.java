package team.cobblestone.gikipedia.server.v1.domain.category.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import team.cobblestone.gikipedia.server.v1.domain.category.entity.Category;

@Getter
@Builder
public class CategoryResponse {

    private Long id;

    private String name;

    private String description;

    private Long parentId;

    private List<CategoryResponse> children;

    private Integer documentCount;

    private LocalDateTime createdAt;

    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder().id(category.getId()).name(category.getName())
                .description(category.getDescription())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .children(category.getChildren().stream().map(CategoryResponse::from).collect(Collectors.toList()))
                .documentCount(category.getDocuments().size()).createdAt(category.getCreatedAt()).build();
    }

}
