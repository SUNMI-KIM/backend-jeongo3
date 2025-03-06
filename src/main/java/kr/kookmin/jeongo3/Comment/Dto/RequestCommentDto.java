package kr.kookmin.jeongo3.Comment.Dto;

import kr.kookmin.jeongo3.Comment.Comment;
import lombok.Getter;

@Getter
public class RequestCommentDto {

    private String postId;
    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .build();
    }
}
