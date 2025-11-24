package team.cobblestone.gikipedia.server.v1.domain.document.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team.cobblestone.gikipedia.server.v1.domain.document.entity.Document;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.DocumentVersion;

public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Long> {

    List<DocumentVersion> findByDocumentOrderByVersionNumberDesc(Document document);

    Optional<DocumentVersion> findByDocumentAndVersionNumber(Document document, Integer versionNumber);

    @Query("SELECT COALESCE(MAX(dv.versionNumber), 0) FROM DocumentVersion dv WHERE dv.document = :document")
    Integer findMaxVersionNumberByDocument(@Param("document") Document document);

}
