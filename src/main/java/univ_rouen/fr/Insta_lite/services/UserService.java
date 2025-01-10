package univ_rouen.fr.Insta_lite.services;


import univ_rouen.fr.Insta_lite.dtos.AppUserRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.AppUserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    AppUserResponseDTO add(AppUserRequestDTO userDTO);
    AppUserResponseDTO get(Long id);
    AppUserResponseDTO update(AppUserRequestDTO userDTO, Long id);
    void delete(Long id);
    List<AppUserResponseDTO> getAllUsers();

    Optional<AppUserResponseDTO> findByEmail(String email);

    AppUserResponseDTO updatePassword(String email, String newPassword);
    Long getIdByEmail(String email);
}
