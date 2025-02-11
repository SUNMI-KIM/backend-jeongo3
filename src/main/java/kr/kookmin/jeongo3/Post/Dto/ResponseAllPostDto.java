package kr.kookmin.jeongo3.Post.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseAllPostDto {

    private String id;
    private String title;
    private String content;

}
