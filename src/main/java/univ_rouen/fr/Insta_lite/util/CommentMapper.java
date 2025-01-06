package univ_rouen.fr.Insta_lite.util;

import univ_rouen.fr.Insta_lite.dtos.CommentDTO;
import univ_rouen.fr.Insta_lite.models.Comment;

public interface CommentMapper {
    Comment convertToEntity(CommentDTO commentDTO);
    CommentDTO convertToDTO(Comment comment);
    void updateEntityWithDto(CommentDTO commentDTO, Comment comment);
}
