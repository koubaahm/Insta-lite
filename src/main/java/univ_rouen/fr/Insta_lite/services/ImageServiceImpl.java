package univ_rouen.fr.Insta_lite.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import univ_rouen.fr.Insta_lite.dtos.ImageDTO;
import univ_rouen.fr.Insta_lite.models.Image;
import univ_rouen.fr.Insta_lite.repository.ImageRepository;
import univ_rouen.fr.Insta_lite.mapper.ImageMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
   private  final ImageMapper imageMapper;

    public ImageServiceImpl(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;

    }

    @Override
    public ImageDTO saveImage(MultipartFile file, ImageDTO imageDTO) {
        try {
            String uploadDir = "src/main/resources/static/images";
            Path filePath = Paths.get(uploadDir, file.getOriginalFilename());

            if (!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }

            Files.copy(file.getInputStream(), filePath);


            String title = getImageNameWithoutExtension(file.getOriginalFilename());
            imageDTO.setTitle(title);

            imageDTO.setPath("/images/" + file.getOriginalFilename());
            imageDTO.setSize(file.getSize());
            imageDTO.setFormat(getExtention(filePath));
            imageDTO.setUploadedAt(LocalDateTime.now());

            Image image = imageMapper.convertToEntity(imageDTO);

            Image savedImage = imageRepository.save(image);

            return imageMapper.convertToDto(savedImage);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'upload de l'image", e);
        }
    }


    @Override
    public List<ImageDTO> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return images.stream().map(imageMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public ImageDTO getImageById(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image introuvable avec id: " + id));
        return imageMapper.convertToDto(image);
    }

    @Override
    public ImageDTO updateImage(ImageDTO imageDTO, Long id) {
        Image existingImage = imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Image introuvable avec l'ID : " + id));

        imageMapper.updateEntityWithDto(existingImage, imageDTO);
        Image updatedImage = imageRepository.save(existingImage);
        return imageMapper.convertToDto(updatedImage);
    }


    @Override
    public void deleteImageById(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));


        Path filePath = Paths.get("src/main/resources/static/images", image.getPath().replace("/images/", ""));

        try {

            if (Files.exists(filePath)) {
                Files.delete(filePath);
            } else {
                throw new RuntimeException("Fichier non trouvé: " + filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + image.getTitle(), e);
        }


        imageRepository.deleteById(id);
    }

    @Override
    public Resource getImageSource(String filename) {
        try {

            Path path = Paths.get("src/main/resources/static/images").resolve(filename);
            Resource resource = new FileSystemResource(path);

            if (!resource.exists()) {
                throw new RuntimeException("Fichier non trouvé: " + filename);
            }

            return resource;

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement de l'image: " + filename, e);
        }
    }
    @Override
    public String getExtention(Path path) {
        try {
            String contentType = Files.probeContentType(path);
            return contentType != null ? contentType : "application/octet-stream"; // valeur par défaut
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la détection du type MIME", e);
        }
    }

    public String getImageNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return fileName;
        }
        return fileName.substring(0, lastDotIndex);
    }





}
