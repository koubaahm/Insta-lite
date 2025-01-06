package univ_rouen.fr.Insta_lite.dtos;

import jakarta.validation.constraints.NotNull;
import univ_rouen.fr.Insta_lite.enumeration.Visibility;

import java.time.LocalDateTime;

public class ImageDTO {


    private String title;
    private String path;
    private Visibility visibility;
    private long size;
    private String format;
    private LocalDateTime uploadedAt;
    @NotNull(message = "uploadedById ne doit pas etre null")
    private Long uploadedById;

    public ImageDTO() {
    }

    public ImageDTO(String title, String path, Visibility visibility, long size, String format, LocalDateTime uploadedAt, Long uploadedById) {

        this.title = title;
        this.path = path;
        this.visibility = visibility;
        this.size = size;
        this.format = format;
        this.uploadedAt = uploadedAt;
        this.uploadedById = uploadedById;
    }



    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public long getSize() {
        return size;
    }

    public String getFormat() {
        return format;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public Long getUploadedById() {
        return uploadedById;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public void setUploadedById(Long uploadedById) {
        this.uploadedById = uploadedById;
    }

    @Override
    public String toString() {
        return "ImageDTO{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", visibility=" + visibility +
                ", size=" + size +
                ", format='" + format + '\'' +
                ", uploadedAt=" + uploadedAt +
                ", uploadedById=" + uploadedById +
                '}';
    }
}
