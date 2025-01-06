package univ_rouen.fr.Insta_lite.services;

import univ_rouen.fr.Insta_lite.dtos.VideoDTO;
import java.util.List;

public interface VideoService {
    VideoDTO saveVideo(VideoDTO videoDTO);
    List<VideoDTO> getAllVideos();
    VideoDTO getVideoById(Long id);
    VideoDTO updateVideo(VideoDTO videoDTO, Long id);
    void deleteVideoById(Long id);
}
