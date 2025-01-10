package univ_rouen.fr.Insta_lite.mapper;

import org.springframework.stereotype.Component;

import univ_rouen.fr.Insta_lite.dtos.ImageRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.ImageResponseDTO;

import univ_rouen.fr.Insta_lite.models.Image;




@Component
public class ImageMapperImpl implements ImageMapper {



    @Override
    public Image convertToEntity(ImageRequestDTO imageDTO) {
        Image image = new Image();
        image.setTitle(imageDTO.getTitle());
        image.setPath(imageDTO.getPath());
        image.setVisibility(imageDTO.getVisibility());
        image.setSize(imageDTO.getSize());
        image.setFormat(imageDTO.getFormat());


        return image;
    }

    @Override
    public ImageResponseDTO convertToDto(Image image) {
        ImageResponseDTO imageDTO = new ImageResponseDTO();
        imageDTO.setTitle(image.getTitle());
        imageDTO.setPath(image.getPath());
        imageDTO.setVisibility(image.getVisibility());
        imageDTO.setSize(image.getSize());
        imageDTO.setFormat(image.getFormat());
        imageDTO.setUploadedAt(image.getUploadedAt());

        if (image.getUploadedBy() != null) {
            imageDTO.setUploadedById(image.getUploadedBy().getId());
        }

        return imageDTO;
    }

    @Override
    public void updateEntityWithDto(Image image, ImageRequestDTO imageDTO) {
        image.setTitle(imageDTO.getTitle());
        image.setPath(imageDTO.getPath());
        image.setVisibility(imageDTO.getVisibility());
        image.setSize(imageDTO.getSize());
        image.setFormat(imageDTO.getFormat());

    }
}
