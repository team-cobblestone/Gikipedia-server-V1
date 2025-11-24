package team.cobblestone.gikipedia.server.v1.domain.document.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import team.cobblestone.gikipedia.server.v1.domain.document.dto.DocumentRequest;
import team.cobblestone.gikipedia.server.v1.domain.document.dto.DocumentResponse;
import team.cobblestone.gikipedia.server.v1.domain.document.dto.DocumentVersionResponse;
import team.cobblestone.gikipedia.server.v1.domain.document.service.DocumentService;
import team.cobblestone.gikipedia.server.v1.global.common.response.CommonApiResponse;

@Tag(name = "Document", description = "문서 관리 API")
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @Operation(summary = "문서 생성", description = "새로운 문서를 생성합니다")
    @PostMapping
    public ResponseEntity<CommonApiResponse<DocumentResponse>> createDocument(
            @Validated @RequestBody DocumentRequest request, @RequestParam Long authorId) {
        DocumentResponse response = documentService.createDocument(request, authorId);
        return ResponseEntity.ok(CommonApiResponse.created("문서가 생성되었습니다", response));
    }

    @Operation(summary = "문서 조회", description = "slug로 문서를 조회합니다")
    @GetMapping("/{slug}")
    public ResponseEntity<CommonApiResponse<DocumentResponse>> getDocument(@PathVariable String slug) {
        DocumentResponse response = documentService.getDocument(slug);
        return ResponseEntity.ok(CommonApiResponse.success("문서 조회 성공", response));
    }

    @Operation(summary = "문서 목록 조회", description = "문서 목록을 페이징하여 조회합니다")
    @GetMapping
    public ResponseEntity<CommonApiResponse<Page<DocumentResponse>>> getDocuments(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<DocumentResponse> response = documentService.getDocuments(pageable);
        return ResponseEntity.ok(CommonApiResponse.success("문서 목록 조회 성공", response));
    }

    @Operation(summary = "문서 검색", description = "키워드로 문서를 검색합니다")
    @GetMapping("/search")
    public ResponseEntity<CommonApiResponse<Page<DocumentResponse>>> searchDocuments(@RequestParam String keyword,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<DocumentResponse> response = documentService.searchDocuments(keyword, pageable);
        return ResponseEntity.ok(CommonApiResponse.success("문서 검색 성공", response));
    }

    @Operation(summary = "최근 수정 문서", description = "최근 수정된 문서 목록을 조회합니다")
    @GetMapping("/recent")
    public ResponseEntity<CommonApiResponse<Page<DocumentResponse>>> getRecentDocuments(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<DocumentResponse> response = documentService.getRecentDocuments(pageable);
        return ResponseEntity.ok(CommonApiResponse.success("최근 문서 조회 성공", response));
    }

    @Operation(summary = "문서 수정", description = "문서를 수정하고 새 버전을 생성합니다")
    @PutMapping("/{slug}")
    public ResponseEntity<CommonApiResponse<DocumentResponse>> updateDocument(@PathVariable String slug,
            @Validated @RequestBody DocumentRequest request, @RequestParam Long authorId) {
        DocumentResponse response = documentService.updateDocument(slug, request, authorId);
        return ResponseEntity.ok(CommonApiResponse.success("문서가 수정되었습니다", response));
    }

    @Operation(summary = "문서 삭제", description = "문서를 soft delete합니다")
    @DeleteMapping("/{slug}")
    public ResponseEntity<CommonApiResponse<Void>> deleteDocument(@PathVariable String slug) {
        documentService.deleteDocument(slug);
        return ResponseEntity.ok(CommonApiResponse.success("문서가 삭제되었습니다"));
    }

    @Operation(summary = "문서 변경 이력", description = "문서의 모든 버전 이력을 조회합니다")
    @GetMapping("/{slug}/history")
    public ResponseEntity<CommonApiResponse<List<DocumentVersionResponse>>> getDocumentHistory(
            @PathVariable String slug) {
        List<DocumentVersionResponse> response = documentService.getDocumentHistory(slug);
        return ResponseEntity.ok(CommonApiResponse.success("문서 이력 조회 성공", response));
    }

    @Operation(summary = "특정 버전 조회", description = "문서의 특정 버전을 조회합니다")
    @GetMapping("/{slug}/versions/{versionNumber}")
    public ResponseEntity<CommonApiResponse<DocumentVersionResponse>> getDocumentVersion(@PathVariable String slug,
            @PathVariable Integer versionNumber) {
        DocumentVersionResponse response = documentService.getDocumentVersion(slug, versionNumber);
        return ResponseEntity.ok(CommonApiResponse.success("버전 조회 성공", response));
    }

    @Operation(summary = "버전 되돌리기", description = "문서를 이전 버전으로 되돌립니다")
    @PostMapping("/{slug}/revert/{versionNumber}")
    public ResponseEntity<CommonApiResponse<DocumentResponse>> revertToVersion(@PathVariable String slug,
            @PathVariable Integer versionNumber, @RequestParam Long authorId) {
        DocumentResponse response = documentService.revertToVersion(slug, versionNumber, authorId);
        return ResponseEntity.ok(CommonApiResponse.success("버전이 되돌려졌습니다", response));
    }

}
