package kr.kookmin.jeongo3.DISC;

import jakarta.persistence.*;
import kr.kookmin.jeongo3.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class DISC {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "DISC_ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Enumerated(EnumType.STRING)
    private DISCCode discCode;

    @Builder // 나중에 dto에서 toentity로 바꾸기
    public DISC(User user, DISCCode discCode) {
        this.user = user;
        this.discCode = discCode;
    }
}