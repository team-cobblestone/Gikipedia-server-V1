package team.cobblestone.gikipedia.server.v1.domain.document.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team.cobblestone.gikipedia.server.v1.domain.document.dto.DocumentRequest;
import team.cobblestone.gikipedia.server.v1.domain.document.dto.DocumentResponse;
import team.cobblestone.gikipedia.server.v1.domain.document.dto.DocumentVersionResponse;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.Document;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.DocumentNamespace;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.DocumentVersion;
import team.cobblestone.gikipedia.server.v1.domain.document.repository.DocumentRepository;
import team.cobblestone.gikipedia.server.v1.domain.document.repository.DocumentVersionRepository;
import team.cobblestone.gikipedia.server.v1.domain.user.entity.User;
import team.cobblestone.gikipedia.server.v1.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentService {

    private final DocumentRepository documentRepository;

    private final DocumentVersionRepository versionRepository;

    private final UserRepository userRepository;

    @Transactional
    public DocumentResponse createDocument(DocumentRequest request, Long authorId) {
        if (documentRepository.existsBySlug(request.getSlug())) {
            throw new IllegalArgumentException("이미 존재하는 슬러그입니다: " + request.getSlug());
        }

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다"));

        Document document = Document.builder().title(request.getTitle()).slug(request.getSlug())
                .namespace(request.getNamespace() != null ? request.getNamespace() : DocumentNamespace.MAIN).build();

        Document savedDocument = documentRepository.save(document);

        DocumentVersion version = DocumentVersion.builder().document(savedDocument).author(author)
                .content(request.getContent()).summary(request.getSummary()).versionNumber(1).build();

        DocumentVersion savedVersion = versionRepository.save(version);
        savedDocument.updateCurrentVersion(savedVersion);

        return DocumentResponse.from(savedDocument);
    }

    public DocumentResponse getDocument(String slug) {
        Document document = documentRepository.findBySlugAndIsDeletedFalse(slug)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문서입니다: " + slug));

        return DocumentResponse.from(document);
    }

    public Page<DocumentResponse> getDocuments(Pageable pageable) {
        return documentRepository.findByIsDeletedFalse(pageable).map(DocumentResponse::from);
    }

    public Page<DocumentResponse> searchDocuments(String keyword, Pageable pageable) {
        return documentRepository.searchDocuments(keyword, null, pageable).map(DocumentResponse::from);
    }

    public Page<DocumentResponse> getRecentDocuments(Pageable pageable) {
        return documentRepository.findRecentDocuments(pageable).map(DocumentResponse::from);
    }

    @Transactional
    public DocumentResponse updateDocument(String slug, DocumentRequest request, Long authorId) {
        Document document = documentRepository.findBySlugAndIsDeletedFalse(slug)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문서입니다: " + slug));

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다"));

        document.updateTitle(request.getTitle());

        Integer nextVersion = versionRepository.findMaxVersionNumberByDocument(document) + 1;

        DocumentVersion version = DocumentVersion.builder().document(document).author(author)
                .content(request.getContent()).summary(request.getSummary()).versionNumber(nextVersion).build();

        DocumentVersion savedVersion = versionRepository.save(version);
        document.updateCurrentVersion(savedVersion);

        return DocumentResponse.from(document);
    }

    @Transactional
    public void deleteDocument(String slug) {
        Document document = documentRepository.findBySlugAndIsDeletedFalse(slug)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문서입니다: " + slug));

        document.softDelete();
    }

    public List<DocumentVersionResponse> getDocumentHistory(String slug) {
        Document document = documentRepository.findBySlugAndIsDeletedFalse(slug)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문서입니다: " + slug));

        return versionRepository.findByDocumentOrderByVersionNumberDesc(document).stream()
                .map(DocumentVersionResponse::from).collect(Collectors.toList());
    }

    public DocumentVersionResponse getDocumentVersion(String slug, Integer versionNumber) {
        Document document = documentRepository.findBySlugAndIsDeletedFalse(slug)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문서입니다: " + slug));

        DocumentVersion version = versionRepository.findByDocumentAndVersionNumber(document, versionNumber)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 버전입니다: " + versionNumber));

        return DocumentVersionResponse.from(version);
    }

    @Transactional
    public DocumentResponse revertToVersion(String slug, Integer versionNumber, Long authorId) {
        Document document = documentRepository.findBySlugAndIsDeletedFalse(slug)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문서입니다: " + slug));

        DocumentVersion targetVersion = versionRepository.findByDocumentAndVersionNumber(document, versionNumber)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 버전입니다: " + versionNumber));

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다"));

        Integer nextVersion = versionRepository.findMaxVersionNumberByDocument(document) + 1;

        DocumentVersion newVersion = DocumentVersion.builder().document(document).author(author)
                .content(targetVersion.getContent()).summary("버전 " + versionNumber + "로 되돌림").versionNumber(nextVersion)
                .build();

        DocumentVersion savedVersion = versionRepository.save(newVersion);
        document.updateCurrentVersion(savedVersion);

        return DocumentResponse.from(document);
    }

}
