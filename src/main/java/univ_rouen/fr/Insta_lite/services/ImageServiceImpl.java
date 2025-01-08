package univ_rouen.fr.Insta_lite.services;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import univ_rouen.fr.Insta_lite.dtos.ImageDTO;
import univ_rouen.fr.Insta_lite.models.Image;
import univ_rouen.fr.Insta_lite.repository.ImageRepository;
import univ_rouen.fr.Insta_lite.util.ImageMapper;

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
            // 1. Définir le chemin où le fichier sera sauvegardé dans le dossier 'static/images'
            String uploadDir = "src/main/resources/static/images";
            Path filePath = Paths.get(uploadDir, file.getOriginalFilename());

            // Créer le répertoire si nécessaire
            if (!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }

            // 2. Sauvegarder le fichier sur le disque
            Files.copy(file.getInputStream(), filePath);

            // 3. Mettre à jour les informations spécifiques au fichier
            imageDTO.setPath("/images/" + file.getOriginalFilename()); // URL publique
            imageDTO.setSize(file.getSize()); // Taille du fichier en octets
            imageDTO.setFormat(getFileExtension(file.getOriginalFilename())); // Format du fichier (extension)
            imageDTO.setUploadedAt(LocalDateTime.now()); // Date et heure actuelles

            // 4. Mapper les données DTO vers une entité Image
            Image image = imageMapper.convertToEntity(imageDTO);

            // 5. Sauvegarder l'image dans la base de données
            Image savedImage = imageRepository.save(image);

            // 6. Retourner le DTO de l'image sauvegardée
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
        imageRepository.deleteById(id);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "unknown";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }



}
