package univ_rouen.fr.Insta_lite.dtos;

import java.time.LocalDateTime;

public class CommentDTO {

    private String content;
    private LocalDateTime createdAt;
    private Long createdById;
    private Long imageId;
    private Long videoId;

    public CommentDTO() {
    }

    public CommentDTO(String content, LocalDateTime createdAt, Long createdById, Long imageId, Long videoId) {
        this.content = content;
        this.createdAt = createdAt;
        this.createdById = createdById;
        this.imageId = imageId;
        this.videoId = videoId;
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
