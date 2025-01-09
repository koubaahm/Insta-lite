package univ_rouen.fr.Insta_lite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import univ_rouen.fr.Insta_lite.dtos.VideoDTO;
import univ_rouen.fr.Insta_lite.enumeration.Visibility;
import univ_rouen.fr.Insta_lite.services.VideoServiceImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoServiceImpl videoService;

    @Autowired
    public VideoController(VideoServiceImpl videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file, @RequestParam("title") String title,@RequestParam("uploadedById") Long uploadedById,@RequestParam("visibility") Visibility visibility) {
        try {
            VideoDTO videoDTO = new VideoDTO();
            videoDTO.setTitle(title);
            videoDTO.setUploadedById(uploadedById);
            videoDTO.setVisibility(visibility);
            videoService.saveVideo(file, videoDTO);


            return ResponseEntity.ok("Vidéo téléchargée avec succès.");
        } catch (Exception e) {

            return ResponseEntity.status(500).body("Erreur lors du téléchargement de la vidéo : " + e.getMessage());
        }
    }



    // Obtenir toutes les vidéos
    @GetMapping
    public List<VideoDTO> getAllVideos() {
        return videoService.getAllVideos();
    }

    // Obtenir une vidéo par son ID
    @GetMapping("/{id}")
    public ResponseEntity<VideoDTO> getVideoById(@PathVariable Long id) {
        VideoDTO videoDTO = videoService.getVideoById(id);
        return ResponseEntity.ok(videoDTO);
    }

    // Mettre à jour une vidéo
    @PutMapping("/{id}")
    public ResponseEntity<VideoDTO> updateVideo(@PathVariable Long id, @RequestBody VideoDTO videoDTO) {
        VideoDTO updatedVideo = videoService.updateVideo(videoDTO, id);
        return ResponseEntity.ok(updatedVideo);
    }

    // Supprimer une vidéo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideoById(id);
        return ResponseEntity.noContent().build();
    }

    // Ouvrir la vidéo dans le navigateur
    @GetMapping("/view/{filename}")
    public ResponseEntity<Resource> viewVideo(@PathVariable String filename) {
        try {
            Path path = Paths.get("src/main/resources/static/videos").resolve(filename);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "video/mp4")  // Définir le type MIME, ajustez selon le type de vidéo
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Télécharger une vidéo
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadVideo(@PathVariable String filename) {
        try {
            Path path = Paths.get("src/main/resources/static/videos").resolve(filename);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, "video/mp4")  // Définir le type MIME, ajustez selon le type de vidéo
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
