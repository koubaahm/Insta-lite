package univ_rouen.fr.Insta_lite.services;


import org.springframework.stereotype.Service;
import univ_rouen.fr.Insta_lite.dtos.CommentDTO;
import univ_rouen.fr.Insta_lite.models.Comment;
import univ_rouen.fr.Insta_lite.repository.CommentRepository;
import univ_rouen.fr.Insta_lite.util.CommentMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;


    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentDTO saveComment(CommentDTO commentDTO) {

        Comment comment = commentMapper.convertToEntity(commentDTO);

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.convertToDTO(savedComment);
    }

    @Override
    public List<CommentDTO> getAllComments() {

        return commentRepository.findAll().stream()
                .map(commentMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CommentDTO> getCommentById(Long id) {

        return commentRepository.findById(id)
                .map(commentMapper::convertToDTO);
    }

    @Override
    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {

        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));

        commentMapper.updateEntityWithDto(commentDTO, existingComment);

        Comment updatedComment = commentRepository.save(existingComment);

        return commentMapper.convertToDTO(updatedComment);
    }

    @Override
    public void deleteCommentById(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Comment not found with id: " + id);
        }
        commentRepository.deleteById(id);
    }
}
