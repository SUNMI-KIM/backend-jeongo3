package kr.kookmin.jeongo3.Post.Dto;

import kr.kookmin.jeongo3.Comment.Dto.ResponseCommentDto;
import kr.kookmin.jeongo3.Post.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ResponsePostDto {

    private String postId;
    private String userId;
    private String univ;
    private String email;
    private String title;
    private String content;
    private boolean like;
    private int likeNumber;
    private int views;
    private int commentNumber;
    private String image;
    private List<ResponseCommentDto> responseCommentDto;

    public ResponsePostDto(Post post) {
        this.postId = post.getId();
        this.userId = post.getUser().getId();
        this.univ = post.getUser().getUniv();
        this.email = post.getUser().getEmail();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.views = post.getViews();
        this.commentNumber = post.getComments().size();
        this.responseCommentDto = post.getComments()
                .stream()
                .map(ResponseCommentDto::new)
                .collect(Collectors.toList());
    }

}
