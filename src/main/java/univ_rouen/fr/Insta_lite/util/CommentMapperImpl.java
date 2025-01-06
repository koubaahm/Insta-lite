package univ_rouen.fr.Insta_lite.util;

import org.springframework.stereotype.Component;
import univ_rouen.fr.Insta_lite.dtos.CommentDTO;
import univ_rouen.fr.Insta_lite.models.AppUser;
import univ_rouen.fr.Insta_lite.models.Comment;
import univ_rouen.fr.Insta_lite.models.Image;
import univ_rouen.fr.Insta_lite.models.Video;
import univ_rouen.fr.Insta_lite.repository.UserRepository;
import univ_rouen.fr.Insta_lite.repository.ImageRepository;
import univ_rouen.fr.Insta_lite.repository.VideoRepository;

import java.util.Optional;

@Component
public class CommentMapperImpl implements CommentMapper {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final VideoRepository videoRepository;

    public CommentMapperImpl(UserRepository userRepository, ImageRepository imageRepository, VideoRepository videoRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.videoRepository = videoRepository;
    }

    @Override
    public Comment convertToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(commentDTO.getCreatedAt());

        Optional<AppUser> createdBy = userRepository.findById(commentDTO.getCreatedById());
        createdBy.ifPresent(comment::setCreatedBy);

        Optional<Image> image = imageRepository.findById(commentDTO.getImageId());
        image.ifPresent(comment::setImage);

        Optional<Video> video = videoRepository.findById(commentDTO.getVideoId());
        video.ifPresent(comment::setVideo);

        return comment;
    }

    @Override
    public CommentDTO convertToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreatedAt(comment.getCreatedAt());

        if (comment.getCreatedBy() != null) {
            commentDTO.setCreatedById(comment.getCreatedBy().getId());
        }

        if (comment.getImage() != null) {
            commentDTO.setImageId(comment.getImage().getId());
        }

        if (comment.getVideo() != null) {
            commentDTO.setVideoId(comment.getVideo().getId());
        }

        return commentDTO;
    }

    @Override
    public void updateEntityWithDto(CommentDTO commentDTO, Comment comment) {

        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(commentDTO.getCreatedAt());

        Optional<AppUser> createdBy = userRepository.findById(commentDTO.getCreatedById());
        createdBy.ifPresent(comment::setCreatedBy);

        Optional<Image> image = imageRepository.findById(commentDTO.getImageId());
        image.ifPresent(comment::setImage);

        Optional<Video> video = videoRepository.findById(commentDTO.getVideoId());
        video.ifPresent(comment::setVideo);
    }
    }




