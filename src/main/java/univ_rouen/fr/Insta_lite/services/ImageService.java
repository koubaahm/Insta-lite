package univ_rouen.fr.Insta_lite.services;

import org.springframework.web.multipart.MultipartFile;
import univ_rouen.fr.Insta_lite.dtos.ImageDTO;
import java.util.List;

public interface ImageService {

    ImageDTO saveImage(MultipartFile file, ImageDTO imageDTO);
    List<ImageDTO> getAllImages();
    ImageDTO getImageById(Long id);
    ImageDTO updateImage(ImageDTO imageDTO, Long id);
    void deleteImageById(Long id);
}
