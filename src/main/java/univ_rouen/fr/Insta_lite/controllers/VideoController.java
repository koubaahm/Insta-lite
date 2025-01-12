package univ_rouen.fr.Insta_lite.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import univ_rouen.fr.Insta_lite.dtos.ImageResponseDTO;
import univ_rouen.fr.Insta_lite.dtos.VideoRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.VideoResponseDTO;
import univ_rouen.fr.Insta_lite.enumeration.Visibility;
import univ_rouen.fr.Insta_lite.models.Video;
import univ_rouen.fr.Insta_lite.services.VideoServiceImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {
    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);
    private final VideoServiceImpl videoService;
    @Value("${video.directory}")
    private String videoDirectory;

    @Autowired
    public VideoController(VideoServiceImpl videoService) {
        this.videoService = videoService;
    }

    @Operation(summary = "Charger une vidéo")
    @PostMapping
    public ResponseEntity<String> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "id de l'utilisateur qui télécharge la vidéo", required = true)
            @RequestParam("uploadedById") Long uploadedById,
            @Parameter(description = "Visibilité de la vidéo", required = true)
            @RequestParam("visibility") Visibility visibility) {
        try {
            logger.info("Début de l'upload de vidéo par l'utilisateur ID : {}", uploadedById);

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                logger.error("Le fichier est invalide ou le nom est vide.");
                return ResponseEntity.status(400).body("Le fichier est invalide ou le nom est vide.");
            }

            logger.info("Nom original du fichier : {}", originalFilename);

            VideoRequestDTO videoDTO = new VideoRequestDTO();
            videoDTO.setTitle(videoService.getVideoNameWithoutExtension(originalFilename));
            videoDTO.setUploadedById(uploadedById);
            videoDTO.setVisibility(visibility);

            logger.debug("Données du DTO avant sauvegarde : {}", videoDTO);

            videoService.saveVideo(file, videoDTO);

            logger.info("Vidéo téléchargée avec succès pour l'utilisateur ID : {}", uploadedById);
            return ResponseEntity.ok("vidéo téléchargée avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors du téléchargement de la vidéo : {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("erreur lors du téléchargement de la vidéo : " + e.getMessage());
        }
    }

    @Operation(summary = "retourne une liste de toutes les vidéos disponibles.")
    @GetMapping
    public List<VideoResponseDTO> getAllVideos() {
        return videoService.getAllVideos();
    }

    @Operation(summary = "récupérer une vidéo par son id", description = "retourne les détails d'une vidéo")
    @GetMapping("/{id}")
    public ResponseEntity<VideoResponseDTO> getVideoById(@PathVariable Long id) {
        VideoResponseDTO videoDTO = videoService.getVideoById(id);
        return ResponseEntity.ok(videoDTO);
    }

    @Operation(summary = "update une vidéo")
    @PutMapping("/{id}")
    public ResponseEntity<VideoResponseDTO> updateVideo(@PathVariable Long id, @RequestBody VideoRequestDTO videoDTO) {
        VideoResponseDTO updatedVideo = videoService.updateVideo(videoDTO, id);
        return ResponseEntity.ok(updatedVideo);
    }

    @Operation(summary = "supprimer une vidéo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideoById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "afficher une vidéo dans le navigateur")
    @GetMapping("/view/{filename}")
    public ResponseEntity<Resource> viewVideo(@PathVariable String filename) {
        try {
            Path path = Paths.get(videoDirectory).resolve(filename);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "télécharger une vidéo", description = "permet de télécharger une vidéo en fournissant son nom de fichier.")
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadVideo(@PathVariable String filename) {
        try {
            Path path = Paths.get(videoDirectory).resolve(filename);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    // recuperer la liste des videos d'un utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VideoResponseDTO>> getVideosByUserId(@PathVariable Long userId) {
        List<VideoResponseDTO> videos = videoService.getVideosByUserId(userId);
        return ResponseEntity.ok(videos);
    }
}
