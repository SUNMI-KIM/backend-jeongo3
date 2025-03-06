package kr.kookmin.jeongo3.PostLike;

import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.Post.Post;
import kr.kookmin.jeongo3.Post.PostRepository;
import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.kookmin.jeongo3.Exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public void savePostLike(String postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new MyException(POST_NOT_FOUND));
        if (postLikeRepository.existsByUserAndPost(user, post)) {
            throw new MyException(BAD_REQUEST);
        }
        postLikeRepository.save(new PostLike(user, post));
    }

    public void deletePostLike(String postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new MyException(POST_NOT_FOUND));
        PostLike postLike = postLikeRepository.findByUserAndPost(user, post).orElseThrow(() -> new MyException(POSTLIKE_NOT_FOUND));
        if (!postLike.getUser().getId().equals(user.getId())) {
            throw new MyException(BAD_REQUEST);
        }
        postLikeRepository.delete(postLike);
    }
}
