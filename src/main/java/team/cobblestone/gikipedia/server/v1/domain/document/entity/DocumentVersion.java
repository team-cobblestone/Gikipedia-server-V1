package team.cobblestone.gikipedia.server.v1.domain.document.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.cobblestone.gikipedia.server.v1.domain.user.entity.User;
import team.cobblestone.gikipedia.server.v1.global.common.BaseTimeEntity;

@Entity
@Table(name = "document_versions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentVersion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 500)
    private String summary;

    @Column(nullable = false)
    private Integer versionNumber;

    @Builder
    public DocumentVersion(Document document, User author, String content, String summary, Integer versionNumber) {
        this.document = document;
        this.author = author;
        this.content = content;
        this.summary = summary;
        this.versionNumber = versionNumber;
    }

}
