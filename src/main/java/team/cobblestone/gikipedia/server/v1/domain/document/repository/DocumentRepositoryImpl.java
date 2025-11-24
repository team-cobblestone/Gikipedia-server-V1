package team.cobblestone.gikipedia.server.v1.domain.document.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.Document;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.DocumentNamespace;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.QDocument;

@RequiredArgsConstructor
public class DocumentRepositoryImpl implements DocumentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Document> searchDocuments(String keyword, DocumentNamespace namespace, Pageable pageable) {
        QDocument document = QDocument.document;

        BooleanExpression predicate = document.isDeleted.isFalse();

        if (keyword != null && !keyword.isEmpty()) {
            predicate = predicate
                    .and(document.title.containsIgnoreCase(keyword).or(document.slug.containsIgnoreCase(keyword)));
        }

        if (namespace != null) {
            predicate = predicate.and(document.namespace.eq(namespace));
        }

        List<Document> content = queryFactory.selectFrom(document).where(predicate).offset(pageable.getOffset())
                .limit(pageable.getPageSize()).orderBy(document.updatedAt.desc()).fetch();

        Long total = queryFactory.select(document.count()).from(document).where(predicate).fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    @Override
    public Page<Document> findRecentDocuments(Pageable pageable) {
        QDocument document = QDocument.document;

        List<Document> content = queryFactory.selectFrom(document).where(document.isDeleted.isFalse())
                .orderBy(document.updatedAt.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

        Long total = queryFactory.select(document.count()).from(document).where(document.isDeleted.isFalse())
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

}
