package team.cobblestone.gikipedia.server.v1.domain.document.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team.cobblestone.gikipedia.server.v1.domain.document.entity.Document;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.DocumentNamespace;

public interface DocumentRepository extends JpaRepository<Document, Long>, DocumentRepositoryCustom {

    Optional<Document> findBySlugAndIsDeletedFalse(String slug);

    Page<Document> findByIsDeletedFalse(Pageable pageable);

    Page<Document> findByNamespaceAndIsDeletedFalse(DocumentNamespace namespace, Pageable pageable);

    @Query("SELECT d FROM Document d WHERE d.isDeleted = false AND (d.title LIKE %:keyword% OR d.slug LIKE %:keyword%)")
    Page<Document> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    boolean existsBySlug(String slug);

}
