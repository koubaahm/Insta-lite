package univ_rouen.fr.Insta_lite.services;

import org.springframework.web.multipart.MultipartFile;

import univ_rouen.fr.Insta_lite.dtos.VideoRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.VideoResponseDTO;

import java.util.List;

public interface VideoService {
    VideoResponseDTO saveVideo(MultipartFile file, VideoRequestDTO videoDTO);
    List<VideoResponseDTO> getAllVideos();
    VideoResponseDTO getVideoById(Long id);
    VideoResponseDTO updateVideo(VideoRequestDTO videoDTO, Long id);
    void deleteVideoById(Long id);

    String getVideoNameWithoutExtension(String fileName);
}
