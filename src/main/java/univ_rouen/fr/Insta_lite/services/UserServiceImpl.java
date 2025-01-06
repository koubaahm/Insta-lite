package univ_rouen.fr.Insta_lite.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import univ_rouen.fr.Insta_lite.dtos.AppUserDTO;
import univ_rouen.fr.Insta_lite.models.AppUser;
import univ_rouen.fr.Insta_lite.repository.UserRepository;
import univ_rouen.fr.Insta_lite.util.UserMapper;

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
    public AppUserDTO add(AppUserDTO userDTO) {
        AppUser user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public AppUserDTO get(Long id) {
        AppUser user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    @Override
    public AppUserDTO update(AppUserDTO userDTO, Long id) {
        AppUser user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
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
    public List<AppUserDTO> getAllUsers() {
        List<AppUser> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<AppUserDTO> findByEmail(String email) {
        Optional<AppUser> user = userRepository.findByEmail(email);
        return user.map(userMapper::toDTO);
    }

    @Override
    public AppUserDTO updatePassword(String email, String newPassword) {
        AppUser user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }
}
