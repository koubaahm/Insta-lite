package univ_rouen.fr.Insta_lite.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import univ_rouen.fr.Insta_lite.models.AppUser;


import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}