package univ_rouen.fr.Insta_lite.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ_rouen.fr.Insta_lite.dtos.CommentRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.CommentResponseDTO;
import univ_rouen.fr.Insta_lite.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "créer un nouveau commentaire par un user spécifié")
    @PostMapping("/{userId}")
    public ResponseEntity<CommentResponseDTO> createComment(
            @Parameter(description = "id user", required = true) @PathVariable Long userId,
            @RequestBody CommentRequestDTO commentDTO) {
        try {
            CommentResponseDTO savedComment = commentService.saveComment(commentDTO, userId);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "récupérer les commentaires")
    @GetMapping
    public ResponseEntity<List<CommentResponseDTO>> getAllComments() {
        List<CommentResponseDTO> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "récupérer un commentaire par son ID")
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> getCommentById(
            @Parameter(description = "id commentaire", required = true) @PathVariable Long id) {
        return commentService.getCommentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "update commentaire")
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(
            @Parameter(description = "id du commentaire à mettre à jour", required = true) @PathVariable Long id,
            @RequestBody CommentRequestDTO commentDTO) {
        try {
            CommentResponseDTO updatedComment = commentService.updateComment(id, commentDTO);
            return ResponseEntity.ok(updatedComment);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "supprimer un commentaire")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(
            @Parameter(description = "id commentaire à supprimer", required = true) @PathVariable Long id) {
        try {
            commentService.deleteCommentById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
