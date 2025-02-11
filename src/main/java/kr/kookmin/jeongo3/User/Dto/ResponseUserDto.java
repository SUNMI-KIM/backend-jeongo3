package kr.kookmin.jeongo3.User.Dto;

import kr.kookmin.jeongo3.User.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserDto {
    private UserRole userRole;

    private String name;

    private String email;

    private String gender;

    private int age;

    private String loginId;

    private String phoneNum;

    private String univ; // 희망 대학, 재학 대학

    private String department; // 대학 과, 희망 과

    private String image;

    private int point;

    @Builder
    public ResponseUserDto(UserRole userRole,
                           String name,
                           String email,
                           String gender,
                           int age,
                           String loginId,
                           String phoneNum,
                           String univ,
                           String department,
                           int point) {
        this.userRole = userRole;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.loginId = loginId;
        this.phoneNum = phoneNum;
        this.univ = univ;
        this.department = department;
        this.point = point;
    }
}
