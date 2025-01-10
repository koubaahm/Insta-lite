package univ_rouen.fr.Insta_lite.dtos;

import jakarta.validation.constraints.NotNull;
import univ_rouen.fr.Insta_lite.enumeration.Visibility;

import java.time.LocalDateTime;

public class ImageResponseDTO {
    private Long id;
    private String title;
    private String path;
    private Visibility visibility;
    private long size;
    private String format;
    private LocalDateTime uploadedAt;
    @NotNull(message = "uploadedById ne doit pas etre null")
    private Long uploadedById;

    public ImageResponseDTO() {
    }

    public ImageResponseDTO(Long id,String title, String path, Visibility visibility, long size, String format, LocalDateTime uploadedAt, Long uploadedById) {
        this.id = id;
        this.title = title;
        this.path = path;
        this.visibility = visibility;
        this.size = size;
        this.format = format;
        this.uploadedAt = uploadedAt;
        this.uploadedById = uploadedById;
    }

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

    public Long getUploadedById() {
        return uploadedById;
    }

    public void setUploadedById(Long uploadedById) {
        this.uploadedById = uploadedById;
    }
}
