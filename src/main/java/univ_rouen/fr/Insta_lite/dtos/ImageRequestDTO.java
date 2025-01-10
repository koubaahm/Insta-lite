package univ_rouen.fr.Insta_lite.dtos;


import univ_rouen.fr.Insta_lite.enumeration.Visibility;



// DTO de requête pour la création ou la mise à jour d'une image
public class ImageRequestDTO {
    private String title;
    private String path;
    private Visibility visibility;
    private long size;
    private String format;

    public ImageRequestDTO() {
    }

    public ImageRequestDTO(String title, String path, Visibility visibility, long size, String format) {
        this.title = title;
        this.path = path;
        this.visibility = visibility;
        this.size = size;
        this.format = format;
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
}