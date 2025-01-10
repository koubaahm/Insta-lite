package univ_rouen.fr.Insta_lite.mapper;

import org.springframework.stereotype.Component;

import univ_rouen.fr.Insta_lite.dtos.CommentRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.CommentResponseDTO;
import univ_rouen.fr.Insta_lite.models.Comment;
import univ_rouen.fr.Insta_lite.models.Image;
import univ_rouen.fr.Insta_lite.models.Video;
import univ_rouen.fr.Insta_lite.repository.ImageRepository;
import univ_rouen.fr.Insta_lite.repository.VideoRepository;

import java.util.Optional;

@Component
public class CommentMapperImpl implements CommentMapper {


    private final ImageRepository imageRepository;
    private final VideoRepository videoRepository;

    public CommentMapperImpl(ImageRepository imageRepository, VideoRepository videoRepository) {

        this.imageRepository = imageRepository;
        this.videoRepository = videoRepository;
    }

    @Override
    public Comment convertToEntity(CommentRequestDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());

        Optional<Image> image = imageRepository.findById(commentDTO.getImageId());
        image.ifPresent(comment::setImage);

        Optional<Video> video = videoRepository.findById(commentDTO.getVideoId());
        video.ifPresent(comment::setVideo);

        return comment;
    }

    @Override
    public CommentResponseDTO convertToDTO(Comment comment) {
        CommentResponseDTO commentDTO = new CommentResponseDTO();
        commentDTO.setId(comment.getId());
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
    public void updateEntityWithDto(CommentRequestDTO commentDTO, Comment comment) {

        comment.setContent(commentDTO.getContent());


        Optional<Image> image = imageRepository.findById(commentDTO.getImageId());
        image.ifPresent(comment::setImage);

        Optional<Video> video = videoRepository.findById(commentDTO.getVideoId());
        video.ifPresent(comment::setVideo);
    }
    }




