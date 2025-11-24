package team.cobblestone.gikipedia.server.v1.domain.document.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import team.cobblestone.gikipedia.server.v1.domain.document.dto.DocumentResponse;
import team.cobblestone.gikipedia.server.v1.domain.document.service.DocumentLinkService;
import team.cobblestone.gikipedia.server.v1.global.common.response.CommonApiResponse;

@Tag(name = "Document Link", description = "문서 링크 관리 API")
@RestController
@RequestMapping("/api/documents/{slug}/links")
@RequiredArgsConstructor
public class DocumentLinkController {

    private final DocumentLinkService linkService;

    @Operation(summary = "역링크 조회", description = "이 문서를 참조하는 문서들을 조회합니다")
    @GetMapping("/backlinks")
    public ResponseEntity<CommonApiResponse<List<DocumentResponse>>> getBacklinks(@PathVariable String slug) {
        List<DocumentResponse> response = linkService.getBacklinks(slug);
        return ResponseEntity.ok(CommonApiResponse.success("역링크 조회 성공", response));
    }

    @Operation(summary = "외부 링크 조회", description = "이 문서가 참조하는 문서들을 조회합니다")
    @GetMapping("/outgoing")
    public ResponseEntity<CommonApiResponse<List<DocumentResponse>>> getOutgoingLinks(@PathVariable String slug) {
        List<DocumentResponse> response = linkService.getOutgoingLinks(slug);
        return ResponseEntity.ok(CommonApiResponse.success("외부 링크 조회 성공", response));
    }

}
