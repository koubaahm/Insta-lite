package univ_rouen.fr.Insta_lite.dtos;

import java.time.LocalDateTime;


public class CommentResponseDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long createdById;
    private Long imageId;
    private Long videoId;

    public CommentResponseDTO() {
    }

    public CommentResponseDTO(Long id, String content, LocalDateTime createdAt, Long createdById, Long imageId, Long videoId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.createdById = createdById;
        this.imageId = imageId;
        this.videoId = videoId;
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

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
}
