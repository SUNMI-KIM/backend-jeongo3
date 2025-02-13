package kr.kookmin.jeongo3.Security.Jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.Exception.ErrorCode;
import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.Security.Jwt.Dto.TokenDto;
import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtRepository jwtRepository;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Transactional
    public TokenDto updateRefreshToken(String accessToken, String refreshToken) {
        String loginId;

        try {
            jwtProvider.validateToken(refreshToken);
            accessToken = jwtProvider.disassembleToken(accessToken);
            loginId = jwtProvider.getUser(accessToken);
        } catch (SignatureException | MalformedJwtException | ArrayIndexOutOfBoundsException e) {
            throw new MyException(ErrorCode.WRONG_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new MyException(ErrorCode.EXPIRED_TOKEN);
        }

        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new MyException(ErrorCode.USER_NOT_FOUND));
        accessToken = jwtProvider.createAccessToken(user.getLoginId(), user.getUserRole());

        Jwt jwt = jwtRepository.findTopByUser(user).orElseThrow(() -> new MyException(ErrorCode.LOGIN_NOT_FOUND));
        if (refreshToken == jwt.getRefreshToken()) {
            throw new MyException(ErrorCode.TOKEN_NOT_FOUND);
        }
        refreshToken = jwtProvider.createRefreshToken();
        jwt.setRefreshToken(refreshToken);
        return new TokenDto(accessToken, refreshToken);
    }

    @Transactional
    public void addRefreshToken(User user, String refreshToken) {
        jwtRepository.deleteByUser(user);
        jwtRepository.save(new Jwt(user, refreshToken));
    }
}
