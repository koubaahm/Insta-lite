package univ_rouen.fr.Insta_lite.mapper;


import univ_rouen.fr.Insta_lite.dtos.CommentRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.CommentResponseDTO;
import univ_rouen.fr.Insta_lite.models.Comment;

public interface CommentMapper {
    Comment convertToEntity(CommentRequestDTO commentDTO);
    CommentResponseDTO convertToDTO(Comment comment);
    void updateEntityWithDto(CommentRequestDTO commentDTO, Comment comment);
}
