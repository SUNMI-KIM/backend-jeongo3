package kr.kookmin.jeongo3.Post;

import kr.kookmin.jeongo3.Post.Dto.PostMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, String>, PostCustomRepository {

    /*@Query(value = "select p from Post p " +
            "inner join PostLike pl on p.id = pl.post.id where p.postType = :postType " +
            "group by p.id having count(pl.id) > 3 order by p.time DESC ") // 숫자 바꿔주기, 시간 추가하기
    Optional<Post> findFirstHotPost(PostType postType);*/

    Slice<PostMapping> findAllByPostTypeOrderByTimeDesc(PostType postType, Pageable pageable);

    /*@Modifying
    @Query("update Post p set p.views = p.views + 1 where p.id = :id")
    int updateViews(@Param("id") String id);*/
}
