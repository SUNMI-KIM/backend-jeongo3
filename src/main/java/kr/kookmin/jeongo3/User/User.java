package kr.kookmin.jeongo3.User;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "USER_ID")
    private String id;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String gender;

    @Column
    private int age;

    @Column
    private String loginId;

    @Column
    private String password;

    @Column
    private String phoneNum;

    @Column
    private String univ; // 희망 대학, 재학 대학

    @Column
    private String department; // 대학 과, 희망 과

    @Column
    private String image;

    @Column
    @ColumnDefault("0")
    private int report;

    @Column
    @ColumnDefault("0")
    private int point;

    @Builder
    public User(UserRole userRole,
                String name,
                String email,
                String gender,
                int age,
                String loginId,
                String password,
                String phoneNum,
                String univ,
                String department,
                String image) {
        this.userRole = userRole;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.loginId = loginId;
        this.password = password;
        this.phoneNum = phoneNum;
        this.univ = univ;
        this.department = department;
        this.image = image;
    }
}
