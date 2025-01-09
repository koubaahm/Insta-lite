package univ_rouen.fr.Insta_lite.mapper;

import univ_rouen.fr.Insta_lite.dtos.AppUserRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.AppUserResponseDTO;
import univ_rouen.fr.Insta_lite.models.AppUser;

public interface UserMapper {

    AppUserResponseDTO toDTO(AppUser user);

    AppUser toEntity(AppUserRequestDTO dto);

    void updateEntityWithDto(AppUserRequestDTO dto, AppUser user);
}

