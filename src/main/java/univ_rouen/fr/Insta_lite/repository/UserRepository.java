package univ_rouen.fr.Insta_lite.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import univ_rouen.fr.Insta_lite.models.AppUser;


import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    //protection contre les injections sql
    @Query("SELECT u FROM AppUser u WHERE u.email = :email")
    Optional<AppUser> findByEmail(@Param("email") String email);

}