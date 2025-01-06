package univ_rouen.fr.Insta_lite.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import univ_rouen.fr.Insta_lite.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}

