package univ_rouen.fr.Insta_lite.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import univ_rouen.fr.Insta_lite.dtos.VideoRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.VideoResponseDTO;
import univ_rouen.fr.Insta_lite.models.Video;
import univ_rouen.fr.Insta_lite.repository.UserRepository;

@Component
public class VideoMapperImpl implements VideoMapper {

    private final UserRepository appUserRepository;

    @Autowired
    public VideoMapperImpl(UserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public Video convertToEntity(VideoRequestDTO videoDTO) {
        Video video = new Video();
        video.setTitle(videoDTO.getTitle());
        video.setPath(videoDTO.getPath());
        video.setVisibility(videoDTO.getVisibility());
        video.setSize(videoDTO.getSize());
        video.setFormat(videoDTO.getFormat());
        video.setDuration(videoDTO.getDuration());

        video.setUploadedBy(appUserRepository.findById(videoDTO.getUploadedById())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + videoDTO.getUploadedById())));
        return video;
    }

    @Override
    public VideoResponseDTO convertToDTO(Video video) {
        VideoResponseDTO videoDTO = new VideoResponseDTO();
        videoDTO.setId(video.getId());
        videoDTO.setTitle(video.getTitle());
        videoDTO.setPath(video.getPath());
        videoDTO.setVisibility(video.getVisibility());
        videoDTO.setSize(video.getSize());
        videoDTO.setFormat(video.getFormat());
        videoDTO.setDuration(video.getDuration());
        videoDTO.setUploadedAt(video.getUploadedAt());
        videoDTO.setUploadedById(video.getUploadedBy().getId());
        return videoDTO;
    }

    @Override
    public void updateEntityWithDto(Video video, VideoRequestDTO videoDTO) {
        video.setTitle(videoDTO.getTitle());
        video.setPath(videoDTO.getPath());
        video.setVisibility(videoDTO.getVisibility());
        video.setSize(videoDTO.getSize());
        video.setFormat(videoDTO.getFormat());
        video.setDuration(videoDTO.getDuration());
        video.setUploadedBy(appUserRepository.findById(videoDTO.getUploadedById())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + videoDTO.getUploadedById())));
    }
}
