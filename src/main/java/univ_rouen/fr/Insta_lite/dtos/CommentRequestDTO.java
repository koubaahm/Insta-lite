package univ_rouen.fr.Insta_lite.dtos;




public class CommentRequestDTO {
    private String content;
    private Long imageId;
    private Long videoId;

    public CommentRequestDTO() {
    }

    public CommentRequestDTO(String content, Long imageId, Long videoId) {
        this.content = content;
        this.imageId = imageId;
        this.videoId = videoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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