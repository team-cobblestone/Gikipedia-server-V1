package team.cobblestone.gikipedia.server.v1.domain.document.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.cobblestone.gikipedia.server.v1.domain.category.entity.Category;
import team.cobblestone.gikipedia.server.v1.global.common.BaseTimeEntity;

@Entity
@Table(name = "documents")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Document extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, unique = true, length = 255)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DocumentNamespace namespace = DocumentNamespace.MAIN;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_version_id")
    private DocumentVersion currentVersion;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
    private List<DocumentVersion> versions = new ArrayList<>();

    @ManyToMany(mappedBy = "documents", fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<>();

    @Builder
    public Document(String title, String slug, DocumentNamespace namespace) {
        this.title = title;
        this.slug = slug;
        this.namespace = namespace != null ? namespace : DocumentNamespace.MAIN;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateCurrentVersion(DocumentVersion version) {
        this.currentVersion = version;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

    public void restore() {
        this.isDeleted = false;
    }

}
