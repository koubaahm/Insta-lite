package univ_rouen.fr.Insta_lite.services;



import univ_rouen.fr.Insta_lite.dtos.CommentRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.CommentResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentResponseDTO saveComment(CommentRequestDTO commentDTO,Long userId);
    List<CommentResponseDTO> getAllComments();
    Optional<CommentResponseDTO> getCommentById(Long id);
    CommentResponseDTO updateComment(Long id, CommentRequestDTO commentDTO);
    void deleteCommentById(Long id);
}
