package univ_rouen.fr.Insta_lite.mapper;

import org.springframework.stereotype.Component;
import univ_rouen.fr.Insta_lite.dtos.AppUserRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.AppUserResponseDTO;
import univ_rouen.fr.Insta_lite.models.AppUser;
import univ_rouen.fr.Insta_lite.models.Comment;
import univ_rouen.fr.Insta_lite.models.Image;
import univ_rouen.fr.Insta_lite.models.Video;

import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public AppUserResponseDTO toDTO(AppUser user) {
        if (user == null) {
            return null;
        }
      
        AppUserResponseDTO dto = new AppUserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setActive(user.isActive());
        dto.setImageIds(user.getImages().stream().map(Image::getId).collect(Collectors.toList()));
        dto.setVideoIds(user.getVideos().stream().map(Video::getId).collect(Collectors.toList()));
        dto.setCommentIds(user.getComments().stream().map(Comment::getId).collect(Collectors.toList()));
        return dto;
    }

    @Override
    public AppUser toEntity(AppUserRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        AppUser user = new AppUser();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setActive(dto.isActive());
        return user;
    }

    @Override
    public void updateEntityWithDto(AppUserRequestDTO dto, AppUser user) {
        if (dto == null || user == null) {
            return;
        }
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(dto.getPassword());
        }
        user.setRole(dto.getRole());
        user.setActive(dto.isActive());
    }
}

