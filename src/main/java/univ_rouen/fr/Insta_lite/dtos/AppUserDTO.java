package univ_rouen.fr.Insta_lite.dtos;

import univ_rouen.fr.Insta_lite.enumeration.AppRole;

import java.util.List;

public class AppUserDTO {

    private String name;
    private String email;
    private String password;
    private AppRole role;
    private boolean isActive;

    // Listes d'identifiants pour éviter les références circulaires
    private List<Long> imageIds;
    private List<Long> videoIds;
    private List<Long> commentIds;

    public AppUserDTO() {
    }

    public AppUserDTO(String name, String email,String password, AppRole role, boolean isActive, List<Long> imageIds, List<Long> videoIds, List<Long> commentIds) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.imageIds = imageIds;
        this.videoIds = videoIds;
        this.commentIds = commentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AppRole getRole() {
        return role;
    }

    public void setRole(AppRole role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<Long> imageIds) {
        this.imageIds = imageIds;
    }

    public List<Long> getVideoIds() {
        return videoIds;
    }

    public void setVideoIds(List<Long> videoIds) {
        this.videoIds = videoIds;
    }

    public List<Long> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(List<Long> commentIds) {
        this.commentIds = commentIds;
    }
}
