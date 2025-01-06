package univ_rouen.fr.Insta_lite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ_rouen.fr.Insta_lite.dtos.VideoDTO;
import univ_rouen.fr.Insta_lite.models.Video;
import univ_rouen.fr.Insta_lite.repository.VideoRepository;
import univ_rouen.fr.Insta_lite.util.VideoMapper;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, VideoMapper videoMapper) {
        this.videoRepository = videoRepository;
        this.videoMapper = videoMapper;
    }

    @Override
    public VideoDTO saveVideo(VideoDTO videoDTO) {
        Video video = videoMapper.convertToEntity(videoDTO);
        Video savedVideo = videoRepository.save(video);
        return videoMapper.convertToDTO(savedVideo);
    }

    @Override
    public List<VideoDTO> getAllVideos() {
        return videoRepository.findAll().stream()
                .map(videoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VideoDTO getVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Video introuvable avec le ID: " + id));
        return videoMapper.convertToDTO(video);
    }

    @Override
    public VideoDTO updateVideo(VideoDTO videoDTO , Long id) {
        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Video introuvable avec le ID: " + id));
        videoMapper.updateEntityWithDto(existingVideo, videoDTO);
        Video updatedVideo = videoRepository.save(existingVideo);
        return videoMapper.convertToDTO(updatedVideo);
    }

    @Override
    public void deleteVideoById(Long id) {
        videoRepository.deleteById(id);
    }
}
