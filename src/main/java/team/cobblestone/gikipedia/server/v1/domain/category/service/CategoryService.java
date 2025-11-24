package team.cobblestone.gikipedia.server.v1.domain.category.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team.cobblestone.gikipedia.server.v1.domain.category.dto.CategoryRequest;
import team.cobblestone.gikipedia.server.v1.domain.category.dto.CategoryResponse;
import team.cobblestone.gikipedia.server.v1.domain.category.entity.Category;
import team.cobblestone.gikipedia.server.v1.domain.category.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        Category parent = null;
        if (request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부모 카테고리입니다"));
        }

        Category category = Category.builder().name(request.getName()).description(request.getDescription())
                .parent(parent).build();

        Category savedCategory = categoryRepository.save(category);
        return CategoryResponse.from(savedCategory);
    }

    public List<CategoryResponse> getRootCategories() {
        return categoryRepository.findByParentIsNull().stream().map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다"));
        return CategoryResponse.from(category);
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다"));

        category.updateName(request.getName());
        category.updateDescription(request.getDescription());

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부모 카테고리입니다"));
            category.updateParent(parent);
        } else {
            category.updateParent(null);
        }

        return CategoryResponse.from(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다"));

        if (!category.getChildren().isEmpty()) {
            throw new IllegalStateException("하위 카테고리가 있는 카테고리는 삭제할 수 없습니다");
        }

        categoryRepository.delete(category);
    }

}
