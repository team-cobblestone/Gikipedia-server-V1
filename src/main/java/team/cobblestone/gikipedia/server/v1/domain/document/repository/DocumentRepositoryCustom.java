package team.cobblestone.gikipedia.server.v1.domain.document.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import team.cobblestone.gikipedia.server.v1.domain.document.entity.Document;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.DocumentNamespace;

public interface DocumentRepositoryCustom {

    Page<Document> searchDocuments(String keyword, DocumentNamespace namespace, Pageable pageable);

    Page<Document> findRecentDocuments(Pageable pageable);

}
