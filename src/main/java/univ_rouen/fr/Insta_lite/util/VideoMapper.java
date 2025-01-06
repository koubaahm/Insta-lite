package univ_rouen.fr.Insta_lite.util;

import univ_rouen.fr.Insta_lite.dtos.VideoDTO;
import univ_rouen.fr.Insta_lite.models.Video;

public interface VideoMapper {
    Video convertToEntity(VideoDTO videoDTO);
    VideoDTO convertToDTO(Video video);
    void updateEntityWithDto(Video video, VideoDTO videoDTO);
}
