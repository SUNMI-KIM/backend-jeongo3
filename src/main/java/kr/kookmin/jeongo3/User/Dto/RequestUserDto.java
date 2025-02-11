package kr.kookmin.jeongo3.User.Dto;

import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRole;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class RequestUserDto {
    private UserRole userRole;

    private String name;

    private String email;

    private String gender;

    private int age;

    private String loginId;

    private String password;

    private String phoneNum;

    private String univ; // 희망 대학, 재학 대학

    private String department; // 대학 과, 희망 과

    private MultipartFile image;

    public User toEntity() {
        return User.builder()
                .userRole(userRole)
                .name(name)
                .email(email)
                .gender(gender)
                .age(age)
                .loginId(loginId)
                .phoneNum(phoneNum)
                .univ(univ)
                .department(department)
                .build();
    }

}
