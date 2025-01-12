package univ_rouen.fr.Insta_lite.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import univ_rouen.fr.Insta_lite.dtos.ImageResponseDTO;
import univ_rouen.fr.Insta_lite.dtos.VideoRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.VideoResponseDTO;
import univ_rouen.fr.Insta_lite.models.AppUser;
import univ_rouen.fr.Insta_lite.models.Image;
import univ_rouen.fr.Insta_lite.models.Video;
import univ_rouen.fr.Insta_lite.repository.UserRepository;
import univ_rouen.fr.Insta_lite.repository.VideoRepository;
import univ_rouen.fr.Insta_lite.mapper.VideoMapper;
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
    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);
    private final VideoUtils videoUtils;
    private final VideoRepository videoRepository;
    private  final UserRepository userRepository;
    private final VideoMapper videoMapper;
    @Value("${video.directory}")
    private String videoDirectory;

    @Autowired
    public VideoServiceImpl(VideoUtils videoUtils, VideoRepository videoRepository, UserRepository userRepository, VideoMapper videoMapper) {
        this.videoUtils = videoUtils;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.videoMapper = videoMapper;
    }

    @Override
    public VideoResponseDTO saveVideo(MultipartFile file, VideoRequestDTO videoDTO) {
        try {
            logger.info("Début du processus d'upload de vidéo...");
            logger.debug("Données du DTO : {}", videoDTO);

            Path filePath = Paths.get(videoDirectory, file.getOriginalFilename());
            AppUser uploadedBy = userRepository.findById(videoDTO.getUploadedById())
                    .orElseThrow(() -> {
                        logger.error("Utilisateur introuvable avec l'ID : {}", videoDTO.getUploadedById());
                        return new RuntimeException("Utilisateur introuvable avec l'ID : " + videoDTO.getUploadedById());
                    });

            // Création des répertoires si nécessaire
            if (!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
                logger.info("Répertoire créé : {}", filePath.getParent());
            }

            // Copie du fichier
            Files.copy(file.getInputStream(), filePath);
            logger.info("Fichier copié avec succès : {}", filePath);

            // Mappage des données supplémentaires
            videoDTO.setPath("/videos/" + file.getOriginalFilename());
            logger.debug("Path set: {}", videoDTO.getPath());
            videoDTO.setSize(file.getSize());
            logger.debug("Size set: {}", videoDTO.getSize());
            videoDTO.setFormat(getExtension(filePath));
            videoDTO.setDuration(videoUtils.getVideoDuration(filePath));
            logger.debug("Duration set: {}", videoUtils.getVideoDuration(filePath));
            logger.debug("Données du DTO après mapping : {}", videoDTO);

            // Conversion en entité et sauvegarde
            Video video = videoMapper.convertToEntity(videoDTO);
            video.setUploadedAt(LocalDateTime.now());
            video.setUploadedBy(uploadedBy);
            logger.debug("Vidéo avant sauvegarde : {}", video);

            Video savedVideo = videoRepository.save(video);
            logger.info("Vidéo sauvegardée avec succès : {}", savedVideo);

            return videoMapper.convertToDTO(savedVideo);
        } catch (Exception e) {
            logger.error("Erreur lors de l'upload de la vidéo : {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de l'upload de la vidéo", e);
        }
    }

    @Override
    public List<VideoResponseDTO> getAllVideos() {
        return videoRepository.findAll().stream()
                .map(videoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VideoResponseDTO getVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Video introuvable avec le ID: " + id));
        return videoMapper.convertToDTO(video);
    }

    @Override
    public VideoResponseDTO updateVideo(VideoRequestDTO videoDTO , Long id) {
        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Video introuvable avec le ID: " + id));
        videoMapper.updateEntityWithDto(existingVideo, videoDTO);
        existingVideo.setUploadedAt(LocalDateTime.now());
        Video updatedVideo = videoRepository.save(existingVideo);
        return videoMapper.convertToDTO(updatedVideo);
    }

    @Override
    public void deleteVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video non trouvée avec l'id: " + id));

        Path filePath = Paths.get(videoDirectory, video.getPath().replace("/videos/", ""));
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
    @Override
    public String getVideoNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return fileName;
        }
        return fileName.substring(0, lastDotIndex);
    }

    // liste des videos d'un utilisateur
    @Override
    public List<VideoResponseDTO> getVideosByUserId(Long userId) {
        List<Video> videos = videoRepository.findByUploadedById(userId);

        if (videos.isEmpty()) {
            throw new RuntimeException("aucune video trouvée pour l'utilisateur avec son id : " + userId);
        }

        return videos.stream()
                .map(videoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public String getExtension(Path path) {
        try {
            String contentType = Files.probeContentType(path);
            return contentType != null ? contentType : "application/octet-stream";
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la détection du type MIME", e);
        }
    }



}
