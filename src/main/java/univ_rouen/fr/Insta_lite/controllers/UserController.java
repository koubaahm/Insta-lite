package univ_rouen.fr.Insta_lite.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ_rouen.fr.Insta_lite.dtos.AppUserRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.AppUserResponseDTO;
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
    public ResponseEntity<AppUserResponseDTO> addUser(@RequestBody AppUserRequestDTO userDTO) {
        AppUserResponseDTO appUser = userService.add(userDTO);
        return ResponseEntity.ok(appUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUserResponseDTO> updateUser(@PathVariable Long id, @RequestBody AppUserRequestDTO userDTO) {
        AppUserResponseDTO appUser = userService.update(userDTO, id);
        return ResponseEntity.ok(appUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AppUserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserResponseDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<AppUserResponseDTO> resetPassword(@RequestParam String email, @RequestBody String newPassword) {
        AppUserResponseDTO updatedUser = userService.updatePassword(email, newPassword);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
