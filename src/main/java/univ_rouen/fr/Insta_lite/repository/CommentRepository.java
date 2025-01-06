package univ_rouen.fr.Insta_lite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import univ_rouen.fr.Insta_lite.models.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long> {
}
