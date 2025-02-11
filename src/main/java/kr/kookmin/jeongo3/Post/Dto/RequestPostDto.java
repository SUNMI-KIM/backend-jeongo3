package kr.kookmin.jeongo3.Post.Dto;

import kr.kookmin.jeongo3.Post.Post;
import kr.kookmin.jeongo3.Post.PostType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RequestPostDto {

    private String id;
    private String title;
    private String content;
    private MultipartFile image;
    private PostType postType;

    public Post toEntity() {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .postType(postType)
                .build();
    }
}
