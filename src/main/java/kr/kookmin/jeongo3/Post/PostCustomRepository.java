package kr.kookmin.jeongo3.Post;

import kr.kookmin.jeongo3.Post.Dto.PostMapping;
import kr.kookmin.jeongo3.Post.Dto.ResponseAllPostDto;
import kr.kookmin.jeongo3.Post.Dto.ResponseHotPostDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface PostCustomRepository {

    Optional<ResponseHotPostDto> findHotPost(PostType postType);

    List<ResponseAllPostDto> findAllPost(PostType postType, Pageable pageable);

    long updateViews(String id);


}
