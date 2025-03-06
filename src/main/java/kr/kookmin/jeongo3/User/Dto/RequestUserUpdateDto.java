package kr.kookmin.jeongo3.User.Dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RequestUserUpdateDto {

    private String name;

    private String univ; // 희망 대학, 재학 대학

    private String department;

    private MultipartFile image;

}
