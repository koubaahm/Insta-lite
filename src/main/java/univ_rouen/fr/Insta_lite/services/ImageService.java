package univ_rouen.fr.Insta_lite.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import univ_rouen.fr.Insta_lite.dtos.ImageRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.ImageResponseDTO;


import java.nio.file.Path;
import java.util.List;

public interface ImageService {

    ImageResponseDTO uploadImage(MultipartFile file, ImageRequestDTO imageDTO,Long userId);
    List<ImageResponseDTO> getAllImages();
    ImageResponseDTO getImageById(Long id);
    ImageResponseDTO updateImage(ImageRequestDTO imageDTO, Long id);
    void deleteImageById(Long id);
    String getExtension(Path path);
    Resource getImageSource(String filename);
    String getImageNameWithoutExtension(String fileName);

}
