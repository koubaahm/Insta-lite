package univ_rouen.fr.Insta_lite.services;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import univ_rouen.fr.Insta_lite.dtos.ImageDTO;
import univ_rouen.fr.Insta_lite.models.Image;
import univ_rouen.fr.Insta_lite.repository.ImageRepository;
import univ_rouen.fr.Insta_lite.util.ImageMapper;

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
    public ImageDTO saveImage(ImageDTO imageDTO) {
        Image image = imageMapper.convertToEntity(imageDTO);
        Image savedImage = imageRepository.save(image);
        return imageMapper.convertToDto(savedImage);
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


}
