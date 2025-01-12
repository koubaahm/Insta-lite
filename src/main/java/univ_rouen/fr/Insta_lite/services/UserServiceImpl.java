package univ_rouen.fr.Insta_lite.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import univ_rouen.fr.Insta_lite.dtos.AppUserRequestDTO;
import univ_rouen.fr.Insta_lite.dtos.AppUserResponseDTO;
import univ_rouen.fr.Insta_lite.exceptions.EmailAlreadyExistsException;
import univ_rouen.fr.Insta_lite.models.AppUser;
import univ_rouen.fr.Insta_lite.repository.UserRepository;
import univ_rouen.fr.Insta_lite.mapper.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public AppUserResponseDTO add(AppUserRequestDTO userDTO) {

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Un utilisateur existe déjà avec cet email : " + userDTO.getEmail());
        }
            AppUser user = userMapper.toEntity(userDTO);
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user = userRepository.save(user);
            return userMapper.toDTO(user);
    }


    @Override
    public AppUserResponseDTO get(Long id) {
        AppUser user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    @Override
    public AppUserResponseDTO update(AppUserRequestDTO userDTO, Long id) {
        AppUser user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Un utilisateur existe déjà avec cet email : " + userDTO.getEmail());
        }
        userMapper.updateEntityWithDto(userDTO, user);
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<AppUserResponseDTO> getAllUsers() {
        List<AppUser> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<AppUserResponseDTO> findByEmail(String email) {
        Optional<AppUser> user = userRepository.findByEmail(email);
        return user.map(userMapper::toDTO);
    }

    @Override
    public AppUserResponseDTO updatePassword(String email, String newPassword) {
        AppUser user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }
    @Override
    public Long getIdByEmail(String email) {
        Optional<AppUser> user = userRepository.findByEmail(email);
        return user.map(AppUser::getId).orElseThrow(() -> new RuntimeException("User not found"));

    }
}
