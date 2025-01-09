package univ_rouen.fr.Insta_lite.mapper;

import univ_rouen.fr.Insta_lite.dtos.ImageDTO;
import univ_rouen.fr.Insta_lite.models.Image;

public interface ImageMapper {
    Image convertToEntity(ImageDTO imageDTO);
    ImageDTO convertToDto(Image image);
    void updateEntityWithDto(Image image, ImageDTO imageDTO);
}
