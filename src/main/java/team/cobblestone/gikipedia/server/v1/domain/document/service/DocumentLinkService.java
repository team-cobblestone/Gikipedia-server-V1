package team.cobblestone.gikipedia.server.v1.domain.document.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team.cobblestone.gikipedia.server.v1.domain.document.dto.DocumentResponse;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.Document;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.DocumentLink;
import team.cobblestone.gikipedia.server.v1.domain.document.repository.DocumentLinkRepository;
import team.cobblestone.gikipedia.server.v1.domain.document.repository.DocumentRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentLinkService {

    private final DocumentLinkRepository linkRepository;

    private final DocumentRepository documentRepository;

    public List<DocumentResponse> getBacklinks(String slug) {
        Document document = documentRepository.findBySlugAndIsDeletedFalse(slug)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문서입니다: " + slug));

        return linkRepository.findByTargetDocument(document).stream().map(DocumentLink::getSourceDocument)
                .map(DocumentResponse::from).collect(Collectors.toList());
    }

    public List<DocumentResponse> getOutgoingLinks(String slug) {
        Document document = documentRepository.findBySlugAndIsDeletedFalse(slug)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문서입니다: " + slug));

        return linkRepository.findBySourceDocument(document).stream().map(DocumentLink::getTargetDocument)
                .map(DocumentResponse::from).collect(Collectors.toList());
    }

}
