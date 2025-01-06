package univ_rouen.fr.Insta_lite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ_rouen.fr.Insta_lite.dtos.ImageDTO;
import univ_rouen.fr.Insta_lite.services.ImageService;

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
    public ResponseEntity<ImageDTO> createImage(@RequestBody ImageDTO imageDTO) {
        System.out.println("Received ImageDTO: " + imageDTO);
        ImageDTO createdImage = imageService.saveImage(imageDTO);
        return new ResponseEntity<>(createdImage, HttpStatus.CREATED);
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
