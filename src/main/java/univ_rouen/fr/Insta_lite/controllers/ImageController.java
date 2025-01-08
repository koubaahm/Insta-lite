package univ_rouen.fr.Insta_lite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import univ_rouen.fr.Insta_lite.dtos.ImageDTO;
import univ_rouen.fr.Insta_lite.enumeration.Visibility;
import univ_rouen.fr.Insta_lite.services.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        List<ImageDTO> images = imageService.getAllImages();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable Long id) {
        ImageDTO image = imageService.getImageById(id);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ImageDTO> createImage(@RequestParam("file") MultipartFile file,
                                                @RequestParam("title") String title,
                                                @RequestParam("visibility") Visibility visibility,
                                                @RequestParam("uploadedById") Long uploadedById) {
        // Créer un ImageDTO à partir des paramètres fournis
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setTitle(title);
        imageDTO.setVisibility(visibility);
        imageDTO.setUploadedById(uploadedById);

        // Appeler le service pour enregistrer l'image
        ImageDTO createdImage = imageService.saveImage(file, imageDTO);

        // Retourner la réponse avec l'image sauvegardée
        return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
    }



    @GetMapping("/image/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            // Chemin du fichier dans le dossier images
            Path path = Paths.get("src/main/resources/static/images").resolve(filename);
            Resource resource = new FileSystemResource(path);

            if (!resource.exists()) {
                throw new RuntimeException("Fichier non trouvé: " + filename);
            }

            // Détecter dynamiquement le type MIME du fichier
            String contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = "application/octet-stream"; // Valeur par défaut si le type MIME ne peut pas être déterminé
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"") // Affichage dans le navigateur
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement de l'image: " + filename, e);
        }
    }




    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> updateImage(@PathVariable Long id, @RequestBody ImageDTO imageDTO) {
        ImageDTO updatedImageDTO = imageService.updateImage(imageDTO,id);
        return new ResponseEntity<>(updatedImageDTO, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImageById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
