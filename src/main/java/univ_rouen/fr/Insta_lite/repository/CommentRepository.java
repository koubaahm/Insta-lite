package univ_rouen.fr.Insta_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import univ_rouen.fr.Insta_lite.models.Comment;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByImageId(Long imageId);
    List<Comment> findByVideoId(Long videoId);
}
