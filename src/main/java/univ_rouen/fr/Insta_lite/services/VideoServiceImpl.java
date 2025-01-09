package univ_rouen.fr.Insta_lite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import univ_rouen.fr.Insta_lite.dtos.VideoDTO;
import univ_rouen.fr.Insta_lite.models.Video;
import univ_rouen.fr.Insta_lite.repository.VideoRepository;
import univ_rouen.fr.Insta_lite.util.VideoMapper;
import jakarta.persistence.EntityNotFoundException;
import univ_rouen.fr.Insta_lite.util.VideoUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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
    public VideoDTO saveVideo(MultipartFile file, VideoDTO videoDTO) {
        try {
            String uploadDir = "src/main/resources/static/videos";
            Path filePath = Paths.get(uploadDir, file.getOriginalFilename());

            if (!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }

            Files.copy(file.getInputStream(), filePath);

            videoDTO.setPath("/videos/" + file.getOriginalFilename());
            videoDTO.setSize(file.getSize());
            videoDTO.setFormat(getExtention(filePath));
            videoDTO.setUploadedAt(LocalDateTime.now());

            // Ajouter un journal avant l'analyse de la vidéo
            System.out.println("Analyse de la durée de la vidéo avec FFprobe...");
            videoDTO.setDuration(VideoUtils.getVideoDuration(filePath));

            Video video = videoMapper.convertToEntity(videoDTO);
            Video savedVideo = videoRepository.save(video);

            return videoMapper.convertToDTO(savedVideo);
        } catch (Exception e) {
            // Journal détaillé de l'erreur
            System.err.println("Erreur dans saveVideo: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'upload de la vidéo", e);
        }
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
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video non trouvée avec l'id: " + id));

        Path filePath = Paths.get("src/main/resources/static/videos", video.getPath().replace("/videos/", ""));
        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            } else {
                throw new RuntimeException("Fichier vidéo non trouvé: " + filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la suppression du fichier vidéo: " + video.getTitle(), e);
        }

        videoRepository.deleteById(id);
    }

    public String getVideoNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return fileName;
        }
        return fileName.substring(0, lastDotIndex);
    }

    public String getExtention(Path path) {
        try {
            String contentType = Files.probeContentType(path);
            return contentType != null ? contentType : "application/octet-stream";
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la détection du type MIME", e);
        }
    }



}
