package univ_rouen.fr.Insta_lite.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false)
    private AppUser createdBy;

    @ManyToOne
    @JoinColumn(name = "image_id", nullable = true)
    private Image image;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = true)
    private Video video;

    @PrePersist
    @PreUpdate
    private void validateCommentAssociation() {
        if (image != null && video != null) {
            throw new IllegalStateException("le commentaire ne peut pas être associé à la fois à une image et une vidéo.");
        }
    }

    public Comment() {
    }

    public Comment(String content, LocalDateTime createdAt, AppUser createdBy, Image image, Video video) {
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.image = image;
        this.video = video;
    }

    public Comment(String content, LocalDateTime createdAt, AppUser createdBy, Image image) {
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.image = image;
        this.video = null;
    }

    public Comment(String content, LocalDateTime createdAt, AppUser createdBy, Video video) {
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.video = video;
        this.image = null;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public AppUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AppUser createdBy) {
        this.createdBy = createdBy;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
