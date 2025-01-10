package univ_rouen.fr.Insta_lite.mapper;


import univ_rouen.fr.Insta_lite.dtos.ImageRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.ImageResponseDTO;
import univ_rouen.fr.Insta_lite.models.Image;

public interface ImageMapper {
    Image convertToEntity(ImageRequestDTO imageDTO);
    ImageResponseDTO convertToDto(Image image);
    void updateEntityWithDto(Image image, ImageRequestDTO imageDTO);
}
