package univ_rouen.fr.Insta_lite.mapper;

import univ_rouen.fr.Insta_lite.dtos.AppUserDTO;
import univ_rouen.fr.Insta_lite.models.AppUser;

public interface UserMapper {

    AppUserDTO toDTO(AppUser user);

    AppUser toEntity(AppUserDTO dto);

    void updateEntityWithDto(AppUserDTO dto, AppUser user);
}

