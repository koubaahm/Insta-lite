package univ_rouen.fr.Insta_lite.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import univ_rouen.fr.Insta_lite.models.Video;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByUploadedById(Long userId);
}

