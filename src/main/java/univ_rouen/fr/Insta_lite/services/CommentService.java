package univ_rouen.fr.Insta_lite.services;

import univ_rouen.fr.Insta_lite.dtos.CommentDTO;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentDTO saveComment(CommentDTO commentDTO);
    List<CommentDTO> getAllComments();
    Optional<CommentDTO> getCommentById(Long id);
    CommentDTO updateComment(Long id, CommentDTO commentDTO);
    void deleteCommentById(Long id);
}
