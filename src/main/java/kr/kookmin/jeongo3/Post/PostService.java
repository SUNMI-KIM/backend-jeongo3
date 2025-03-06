package kr.kookmin.jeongo3.Post;

import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.Aws.S3Service;
import kr.kookmin.jeongo3.Exception.ErrorCode;
import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.Post.Dto.*;
import kr.kookmin.jeongo3.PostLike.PostLikeRepository;
import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRepository;
import kr.kookmin.jeongo3.User.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static kr.kookmin.jeongo3.Exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final S3Service s3Service;

    public String savePost(RequestPostDto requestPostDto, User user) {
        Post post = requestPostDto.toEntity();
        post.setUser(user);

        if (post.getPostType() == PostType.QNA)
            if (user.getUserRole() == UserRole.UNIV)
                throw new MyException(ACCESS_DENIED);

        if (requestPostDto.getImage() != null) {
            String fileName = UUID.randomUUID() + requestPostDto.getImage().getOriginalFilename();
            try {
                s3Service.upload(requestPostDto.getImage(), fileName);
                post.setImage(fileName);
            } catch (IOException e) {

            }
        }
        postRepository.save(post);
        return post.getId();
    }

    public void deletePost(String id, User user) {
        if (!postRepository.findById(id).orElseThrow(() -> new MyException(POST_NOT_FOUND)).getUser().getId().equals(user.getId())) {
            throw new MyException(ACCESS_DENIED);
        }
        postRepository.deleteById(id);
    }

    public List<ResponseAllPostDto> findAllPost(PostType postType, Pageable pageable) {
        return postRepository.findAllPost(postType, pageable);
    }

    @Transactional
    public void updatePost(RequestPostDto requestPostDto, User user) {
        Post post = postRepository.findById(requestPostDto.getId()).orElseThrow(() -> new MyException(POST_NOT_FOUND));
        if (!post.getUser().getId().equals(user.getId())) {
            throw new MyException(BAD_REQUEST);
        }
        post.setTitle(requestPostDto.getTitle());
        post.setContent(requestPostDto.getContent());

        if (requestPostDto.getImage() != null) {
            try {
                String fileName = UUID.randomUUID() + requestPostDto.getImage().getOriginalFilename();
                s3Service.delete(post.getImage());
                s3Service.upload(requestPostDto.getImage(), fileName);
                post.setImage(fileName);
            } catch (IOException e) {

            }
        }
    }

    @Transactional
    public ResponsePostDto findPost(String postId, User user) {
        postRepository.updateViews(postId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new MyException(POST_NOT_FOUND));
        ResponsePostDto responsePostDto = new ResponsePostDto(post);
        responsePostDto.setLike(postLikeRepository.existsByUserAndPost(user, post));
        responsePostDto.setLikeNumber(postLikeRepository.countByPost_Id(post.getId()));

        if (post.getImage() != null) {
            responsePostDto.setImage(s3Service.getPresignedURL(post.getImage()));
        }
        return responsePostDto;
    }

    public ResponseHotPostDto findHotPost(PostType postType) {
        ResponseHotPostDto post = postRepository.findHotPost(postType).orElseThrow(() -> new MyException(POST_NOT_FOUND));
        post.setLikeNumber(postLikeRepository.countByPost_Id(post.getPostId()));
        return post;
    }
}
