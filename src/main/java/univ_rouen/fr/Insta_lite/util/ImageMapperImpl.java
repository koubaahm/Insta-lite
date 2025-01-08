package univ_rouen.fr.Insta_lite.util;

import org.springframework.stereotype.Component;
import univ_rouen.fr.Insta_lite.dtos.ImageDTO;
import univ_rouen.fr.Insta_lite.models.AppUser;
import univ_rouen.fr.Insta_lite.models.Image;
import univ_rouen.fr.Insta_lite.repository.UserRepository;

import java.util.Optional;

@Component
public class ImageMapperImpl implements ImageMapper {

    private final UserRepository userRepository;

    public ImageMapperImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Image convertToEntity(ImageDTO imageDTO) {
        Image image = new Image();
        image.setTitle(imageDTO.getTitle());
        image.setPath(imageDTO.getPath());
        image.setVisibility(imageDTO.getVisibility());
        image.setSize(imageDTO.getSize());
        image.setFormat(imageDTO.getFormat());
        image.setUploadedAt(imageDTO.getUploadedAt());

        Optional<AppUser> uploadedBy = userRepository.findById(imageDTO.getUploadedById());
        uploadedBy.ifPresent(image::setUploadedBy);

        return image;
    }

    @Override
    public ImageDTO convertToDto(Image image) {
        ImageDTO imageDTO = new ImageDTO();
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
    public void updateEntityWithDto(Image image, ImageDTO imageDTO) {
        image.setTitle(imageDTO.getTitle());
        image.setPath(imageDTO.getPath());
        image.setVisibility(imageDTO.getVisibility());
        image.setSize(imageDTO.getSize());
        image.setFormat(imageDTO.getFormat());
        image.setUploadedAt(imageDTO.getUploadedAt());
        Optional<AppUser> uploadedBy = userRepository.findById(imageDTO.getUploadedById());
        uploadedBy.ifPresent(image::setUploadedBy);
    }
}
