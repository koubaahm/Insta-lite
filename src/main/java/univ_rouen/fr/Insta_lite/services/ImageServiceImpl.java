package univ_rouen.fr.Insta_lite.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import univ_rouen.fr.Insta_lite.dtos.ImageRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.ImageResponseDTO;
import univ_rouen.fr.Insta_lite.models.AppUser;
import univ_rouen.fr.Insta_lite.models.Image;
import univ_rouen.fr.Insta_lite.repository.ImageRepository;
import univ_rouen.fr.Insta_lite.mapper.ImageMapper;
import univ_rouen.fr.Insta_lite.repository.UserRepository;

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
    private final ImageMapper imageMapper;
    private final UserRepository userRepository;

    @Value("${image.directory}")
    private String imageDirectory;

    public ImageServiceImpl(ImageRepository imageRepository, ImageMapper imageMapper, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
        this.userRepository = userRepository;
    }
    // upload image
    @Override
    public ImageResponseDTO uploadImage(MultipartFile file, ImageRequestDTO imageDTO, Long userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable avec l'ID: " + userId));

        try {
            Path directoryPath = Paths.get(imageDirectory);
            Files.createDirectories(directoryPath);

            Path filePath = directoryPath.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath);

            imageDTO.setTitle(getImageNameWithoutExtension(file.getOriginalFilename()));
            imageDTO.setPath("/images/" + file.getOriginalFilename());
            imageDTO.setSize(file.getSize());
            imageDTO.setFormat(getExtension(filePath));

            Image image = imageMapper.convertToEntity(imageDTO);
            image.setUploadedBy(user);
            image.setUploadedAt(LocalDateTime.now());
            Image savedImage = imageRepository.save(image);

            return imageMapper.convertToDto(savedImage);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'upload de l'image", e);
        }
    }

    // liste des images
    @Override
    public List<ImageResponseDTO> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return images.stream().map(imageMapper::convertToDto).collect(Collectors.toList());
    }
    // image par son id
    @Override
    public ImageResponseDTO getImageById(Long id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image introuvable avec id: " + id));
        return imageMapper.convertToDto(image);
    }
    // modifier image
    @Override
    public ImageResponseDTO updateImage(ImageRequestDTO imageDTO, Long id) {
        Image existingImage = imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Image introuvable avec l'ID : " + id));

        imageMapper.updateEntityWithDto(existingImage, imageDTO);
        Image updatedImage = imageRepository.save(existingImage);
        return imageMapper.convertToDto(updatedImage);
    }

    // supprimer image
    @Override
    public void deleteImageById(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));


        Path filePath = Paths.get(imageDirectory, image.getPath().replace("/images/", ""));

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

    //renvoi l'image sur le navigateur
    @Override
    public Resource getImageSource(String filename) {
        try {

            Path path = Paths.get(imageDirectory).resolve(filename);
            System.out.println("aaaaaaaaaaaaaaaa"+path);
            Resource resource = new FileSystemResource(path);
            System.out.println("bbbbbbbbbbbbbbbbb"+resource);
            if (!resource.exists()) {
                throw new RuntimeException("Fichier non trouvé: " + filename);
            }

            return resource;

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement de l'image: " + filename, e);
        }
    }
    //extraire l'extension de l'image

    @Override
    public String getExtension(Path path) {
        try {
            String contentType = Files.probeContentType(path);
            if (contentType != null) {
                return contentType;
            }
            return "application/octet-stream";
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la détection du type MIME", e);
        }
    }


    @Override
    public String getImageNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return fileName;
        }
        return fileName.substring(0, lastDotIndex);
    }





}
