package team.cobblestone.gikipedia.server.v1.domain.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team.cobblestone.gikipedia.server.v1.domain.document.entity.Document;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.DocumentLink;

public interface DocumentLinkRepository extends JpaRepository<DocumentLink, Long> {

    List<DocumentLink> findBySourceDocument(Document sourceDocument);

    List<DocumentLink> findByTargetDocument(Document targetDocument);

    void deleteBySourceDocument(Document sourceDocument);

}
