package kr.kookmin.jeongo3.Security.Jwt;

import jakarta.persistence.*;
import kr.kookmin.jeongo3.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Jwt {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "JWT_ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column
    private String refreshToken;

    public Jwt(User user, String refreshToken) {
        this.user = user;
        this.refreshToken = refreshToken;
    }

}
