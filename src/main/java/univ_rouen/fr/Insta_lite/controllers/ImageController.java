package univ_rouen.fr.Insta_lite.controllers;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import univ_rouen.fr.Insta_lite.dtos.ImageRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.ImageResponseDTO;
import univ_rouen.fr.Insta_lite.enumeration.Visibility;
import univ_rouen.fr.Insta_lite.services.ImageService;
import org.springframework.core.io.Resource;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @Operation(summary = "toutes les images", description = "Retourne une liste de toutes les images disponibles.")
    @GetMapping
    public ResponseEntity<List<ImageResponseDTO>> getAllImages() {
        List<ImageResponseDTO> images = imageService.getAllImages();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @Operation(summary = " image par ID", description = "retourne une image par son id")
    @GetMapping("/{id}")
    public ResponseEntity<ImageResponseDTO> getImageById(@PathVariable Long id) {
        ImageResponseDTO image = imageService.getImageById(id);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }


    @Operation(summary = "chargé une image")
    @PostMapping("/upload")
    public ResponseEntity<ImageResponseDTO> createImage(
            @RequestParam("file") MultipartFile file,
            @Parameter(description = " visibilité de l'image", required = true)
            @RequestParam("visibility") Visibility visibility,
            @Parameter(description = "ID de l'utilisateur qui charge l'image", required = true)
            @RequestParam("uploadedById") Long uploadedById) {
        try {
            ImageRequestDTO imageRequestDTO = new ImageRequestDTO();
            imageRequestDTO.setTitle(file.getOriginalFilename());
            imageRequestDTO.setVisibility(visibility);
            imageRequestDTO.setSize(file.getSize());

            ImageResponseDTO createdImage = imageService.uploadImage(file, imageRequestDTO, uploadedById);

            return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @Operation(summary = "afficher l'image sur le navigateur", description = "retourne une image pour être affichée dans le navigateur.")
    @GetMapping("/image/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {

        try {
            Resource resource = imageService.getImageSource(filename);

            Path path = resource.getFile().toPath();
            String contentType = imageService.getExtension(path);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("erreur lors du chargement de l'image: " + filename, e);
        }
    }

    @Operation(summary = "update une image", description = "update les détails d'une image existante")
    @PutMapping("/{id}")
    public ResponseEntity<ImageResponseDTO> updateImage(@PathVariable Long id, @RequestBody ImageRequestDTO imageDTO) {
        ImageResponseDTO updatedImageDTO = imageService.updateImage(imageDTO, id);
        return new ResponseEntity<>(updatedImageDTO, HttpStatus.OK);
    }

    @Operation(summary = "supprimer une image", description = "supprime une image  par son id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImageById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "télécharger une image")
    @GetMapping("/download/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> downloadImage(@PathVariable String filename) {
        try {
            Resource resource = imageService.getImageSource(filename);
            Path path = resource.getFile().toPath();
            String contentType = imageService.getExtension(path);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("erreur lors du chargement de l'image: " + filename, e);
        }
    }
    // recuperer la liste des images d'un utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ImageResponseDTO>> getImagesByUserId(@PathVariable Long userId) {
        List<ImageResponseDTO> images = imageService.getImagesByUserId(userId);
        return ResponseEntity.ok(images);
    }
}
