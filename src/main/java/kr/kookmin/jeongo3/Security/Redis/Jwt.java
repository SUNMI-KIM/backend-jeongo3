package kr.kookmin.jeongo3.Security.Redis;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "token", timeToLive = 604800) /// 7일
public class Jwt {
    @Id
    private String loginId;
    private String refreshToken;

    public Jwt(String loginId, String refreshToken) {
        this.loginId = loginId;
        this.refreshToken = refreshToken;
    }
}
