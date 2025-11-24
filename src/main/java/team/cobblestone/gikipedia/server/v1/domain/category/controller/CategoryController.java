package team.cobblestone.gikipedia.server.v1.domain.category.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import team.cobblestone.gikipedia.server.v1.domain.category.dto.CategoryRequest;
import team.cobblestone.gikipedia.server.v1.domain.category.dto.CategoryResponse;
import team.cobblestone.gikipedia.server.v1.domain.category.service.CategoryService;
import team.cobblestone.gikipedia.server.v1.global.common.response.CommonApiResponse;

@Tag(name = "Category", description = "카테고리 관리 API")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 생성", description = "새로운 카테고리를 생성합니다")
    @PostMapping
    public ResponseEntity<CommonApiResponse<CategoryResponse>> createCategory(
            @Validated @RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return ResponseEntity.ok(CommonApiResponse.created("카테고리가 생성되었습니다", response));
    }

    @Operation(summary = "루트 카테고리 목록", description = "최상위 카테고리 목록을 조회합니다")
    @GetMapping
    public ResponseEntity<CommonApiResponse<List<CategoryResponse>>> getRootCategories() {
        List<CategoryResponse> response = categoryService.getRootCategories();
        return ResponseEntity.ok(CommonApiResponse.success("루트 카테고리 조회 성공", response));
    }

    @Operation(summary = "카테고리 조회", description = "ID로 카테고리를 조회합니다 (하위 카테고리 포함)")
    @GetMapping("/{id}")
    public ResponseEntity<CommonApiResponse<CategoryResponse>> getCategory(@PathVariable Long id) {
        CategoryResponse response = categoryService.getCategory(id);
        return ResponseEntity.ok(CommonApiResponse.success("카테고리 조회 성공", response));
    }

    @Operation(summary = "카테고리 수정", description = "카테고리 정보를 수정합니다")
    @PutMapping("/{id}")
    public ResponseEntity<CommonApiResponse<CategoryResponse>> updateCategory(@PathVariable Long id,
            @Validated @RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(CommonApiResponse.success("카테고리가 수정되었습니다", response));
    }

    @Operation(summary = "카테고리 삭제", description = "카테고리를 삭제합니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(CommonApiResponse.success("카테고리가 삭제되었습니다"));
    }

}
