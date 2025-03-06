package kr.kookmin.jeongo3.Post.Dto;

import kr.kookmin.jeongo3.Post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseHotPostDto {

    String postId;
    String title;
    String content;
    int likeNumber;

    public ResponseHotPostDto(Post post, int likeNumber) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.likeNumber = likeNumber;
    }

}
