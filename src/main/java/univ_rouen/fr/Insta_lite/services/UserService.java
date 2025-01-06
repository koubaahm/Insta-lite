package univ_rouen.fr.Insta_lite.services;


import univ_rouen.fr.Insta_lite.dtos.AppUserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    AppUserDTO add(AppUserDTO userDTO);
    AppUserDTO get(Long id);
    AppUserDTO update(AppUserDTO userDTO, Long id);
    void delete(Long id);
    List<AppUserDTO> getAllUsers();

    Optional<AppUserDTO> findByEmail(String email);

    AppUserDTO updatePassword(String email, String newPassword);
}
