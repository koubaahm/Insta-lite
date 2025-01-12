package univ_rouen.fr.Insta_lite.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;
import univ_rouen.fr.Insta_lite.enumeration.Visibility;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    @Column(nullable = false)
    private String path;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    private long size;

    private String format;

    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "uploaded_by_id", nullable = false)
    private AppUser uploadedBy;

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public AppUser getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(AppUser uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
