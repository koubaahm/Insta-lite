package univ_rouen.fr.Insta_lite.dtos;

import univ_rouen.fr.Insta_lite.enumeration.AppRole;


public class AppUserRequestDTO {


    private String name;
    private String email;
    private String password;
    private AppRole role;
    private boolean isActive;



    public AppUserRequestDTO() {
    }

    public AppUserRequestDTO(String name, String email, String password, AppRole role, boolean isActive) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActive = isActive;

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


}
