package team.cobblestone.gikipedia.server.v1.domain.document.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.cobblestone.gikipedia.server.v1.global.common.BaseTimeEntity;

@Entity
@Table(name = "document_links")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentLink extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_document_id", nullable = false)
    private Document sourceDocument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_document_id", nullable = false)
    private Document targetDocument;

    @Builder
    public DocumentLink(Document sourceDocument, Document targetDocument) {
        this.sourceDocument = sourceDocument;
        this.targetDocument = targetDocument;
    }

}
