package univ_rouen.fr.Insta_lite.services;


import org.springframework.stereotype.Service;
import univ_rouen.fr.Insta_lite.dtos.CommentRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.CommentResponseDTO;
import univ_rouen.fr.Insta_lite.models.AppUser;
import univ_rouen.fr.Insta_lite.models.Comment;
import univ_rouen.fr.Insta_lite.repository.CommentRepository;
import univ_rouen.fr.Insta_lite.mapper.CommentMapper;
import univ_rouen.fr.Insta_lite.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;

    private final UserRepository userRepository;


    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
    }

    @Override
    public CommentResponseDTO saveComment(CommentRequestDTO commentDTO, Long userId) {
        Optional<AppUser> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("utilisateur introuvable avec le id: " + userId);
        }
        AppUser user = userOptional.get();
        Comment comment = commentMapper.convertToEntity(commentDTO);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setCreatedBy(user);

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.convertToDTO(savedComment);
    }

    @Override
    public List<CommentResponseDTO> getAllComments() {

        return commentRepository.findAll().stream()
                .map(commentMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CommentResponseDTO> getCommentById(Long id) {

        return commentRepository.findById(id)
                .map(commentMapper::convertToDTO);
    }

    @Override
    public CommentResponseDTO updateComment(Long id, CommentRequestDTO commentDTO) {

        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentaire introuvable avec le id: " + id));

        commentMapper.updateEntityWithDto(commentDTO, existingComment);

        Comment updatedComment = commentRepository.save(existingComment);

        return commentMapper.convertToDTO(updatedComment);
    }

    @Override
    public void deleteCommentById(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Commentaire introuvable avec le id: " + id);
        }
        commentRepository.deleteById(id);
    }
}
