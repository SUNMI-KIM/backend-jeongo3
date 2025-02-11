package kr.kookmin.jeongo3.PostLike;

import kr.kookmin.jeongo3.Post.Post;
import kr.kookmin.jeongo3.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, String> {

    boolean existsByUserAndPost(User user, Post post);
    int countByPost_Id(String id);

    Optional<PostLike> findByUserAndPost(User user, Post post);

}
