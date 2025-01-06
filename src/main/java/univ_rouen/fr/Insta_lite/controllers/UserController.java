package univ_rouen.fr.Insta_lite.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ_rouen.fr.Insta_lite.dtos.AppUserDTO;
import univ_rouen.fr.Insta_lite.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<AppUserDTO> addUser(@RequestBody AppUserDTO userDTO) {
        AppUserDTO appUser = userService.add(userDTO);
        return ResponseEntity.ok(appUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUserDTO> updateUser(@PathVariable Long id, @RequestBody AppUserDTO userDTO) {
        AppUserDTO appUser = userService.update(userDTO, id);
        return ResponseEntity.ok(appUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AppUserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<AppUserDTO> resetPassword(@RequestParam String email, @RequestBody String newPassword) {
        AppUserDTO updatedUser = userService.updatePassword(email, newPassword);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
