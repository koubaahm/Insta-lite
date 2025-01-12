package univ_rouen.fr.Insta_lite.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "ajout utilisateur")
    @PostMapping
    public ResponseEntity<AppUserResponseDTO> addUser(
            @Valid@RequestBody AppUserRequestDTO userDTO) {
        AppUserResponseDTO appUser = userService.add(userDTO);
        return ResponseEntity.ok(appUser);
    }

    @Operation(summary = "update utilisateur")
    @PutMapping("/{id}")
    public ResponseEntity<AppUserResponseDTO> updateUser(
            @Parameter(description = "id l'utilisateur à mettre à jour", required = true)
            @PathVariable Long id,
            @Valid @RequestBody AppUserRequestDTO userDTO) {
        AppUserResponseDTO appUser = userService.update(userDTO, id);
        return ResponseEntity.ok(appUser);
    }

    @Operation(summary = "supprimer un utilisateur")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "id utilisateur à supprimer", required = true)
            @PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "retourne une liste de tous les utilisateurs")
    @GetMapping
    public ResponseEntity<List<AppUserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "récupérer un utilisateur par son id",
            description = "retourne les informations d'un utilisateur")
    @GetMapping("/{id}")
    public ResponseEntity<AppUserResponseDTO> getUser(
            @Parameter(description = "id utilisateur à récupérer", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @Operation(summary = "réinitialiser le mot de passe",
            description = "réinitialise le mot de passe d'un utilisateur a partir de son email.")
    @PutMapping("/reset-password")
    public ResponseEntity<AppUserResponseDTO> resetPassword(
            @Parameter(description = "email de l'utilisateur dont le mot de passe doit être réinitialisé", required = true)
            @RequestParam String email,
            @RequestBody String newPassword) {
        AppUserResponseDTO updatedUser = userService.updatePassword(email, newPassword);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
