package univ_rouen.fr.Insta_lite.mapper;


import univ_rouen.fr.Insta_lite.dtos.VideoRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.VideoResponseDTO;
import univ_rouen.fr.Insta_lite.models.Video;

public interface VideoMapper {
    Video convertToEntity(VideoRequestDTO videoDTO);
    VideoResponseDTO convertToDTO(Video video);
    void updateEntityWithDto(Video video, VideoRequestDTO videoDTO);
}
