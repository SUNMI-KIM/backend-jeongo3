package kr.kookmin.jeongo3.User;

import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.Aws.S3Service;
import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.Security.Jwt.Jwt;
import kr.kookmin.jeongo3.Security.Jwt.JwtProvider;
import kr.kookmin.jeongo3.Security.Jwt.Dto.TokenDto;
import kr.kookmin.jeongo3.Security.Jwt.JwtRepository;
import kr.kookmin.jeongo3.User.Dto.RequestUserUpdateDto;
import kr.kookmin.jeongo3.User.Dto.RequestUserDto;
import kr.kookmin.jeongo3.User.Dto.ResponseUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static kr.kookmin.jeongo3.Exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtProvider jwtProvider;
    private final JwtRepository jwtRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    public ResponseUserDto findByIdUser(User user) {
        ResponseUserDto responseUserDto = ResponseUserDto.builder()
                .userRole(user.getUserRole())
                .email(user.getEmail())
                .name(user.getName())
                .age(user.getAge())
                .point(user.getPoint())
                .univ(user.getUniv())
                .gender(user.getGender())
                .department(user.getDepartment())
                .phoneNum(user.getPhoneNum())
                .loginId(user.getLoginId())
                .build();
        if (user.getImage() != null) {
            responseUserDto.setImage(s3Service.getPresignedURL(user.getImage()));
        }
        return responseUserDto;
    }

    public boolean validateUser(String loginId) {
        if (userRepository.existsByLoginId(loginId)) {
            throw new MyException(DUPLICATED_USER_ID);
        }
        return true;
    }

    public void saveUser(RequestUserDto requestUserDto) {
        if (userRepository.existsByLoginId(requestUserDto.getLoginId())) {
            throw new MyException(DUPLICATED_USER_ID);
        }
        User user = requestUserDto.toEntity();
        user.setPassword(passwordEncoder.encode(requestUserDto.getPassword()));
        user.setUserRole(requestUserDto.getUserRole());

        if (requestUserDto.getImage() != null) {
            String fileName = UUID.randomUUID() + requestUserDto.getImage().getOriginalFilename();
            try {
                s3Service.upload(requestUserDto.getImage(), fileName);
                user.setImage(fileName);
            } catch (IOException e) {

            }
        }
        userRepository.save(user);
    }

    public List<User> findAllUser() { // 디버깅용
        return userRepository.findAll();
    }

    @Transactional
    public TokenDto loginUser(String loginId, String password) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new MyException(USER_NOT_FOUND));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new MyException(WRONG_PASSWORD);
        }
        String accessToken = jwtProvider.createAccessToken(user.getId(), user.getUserRole());
        String refreshToken;

        if (jwtRepository.existsByUser(user)) {
            Jwt jwt = jwtRepository.findTopByUser(user).orElseThrow(() -> new MyException(LOGIN_NOT_FOUND));
            refreshToken = jwtProvider.createRefreshToken();
            jwt.setRefreshToken(refreshToken);
        }
        else {
            refreshToken = jwtProvider.createRefreshToken();
            jwtRepository.save(new Jwt(user, refreshToken));
        }
        return new TokenDto(accessToken, refreshToken);
    }

    @Transactional
    public long reportUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new MyException(USER_NOT_FOUND);
        }
        return userRepository.updateReport(id);
    }

    @Transactional
    public void updateUser(User user, RequestUserUpdateDto requestUserUpdateDto) {
        user.setName(requestUserUpdateDto.getName());
        user.setUniv(requestUserUpdateDto.getUniv());
        user.setDepartment(requestUserUpdateDto.getDepartment());

        if (requestUserUpdateDto.getImage() != null) {
            String fileName = UUID.randomUUID() + requestUserUpdateDto.getImage().getOriginalFilename();
            try {
                s3Service.delete(user.getImage());
                s3Service.upload(requestUserUpdateDto.getImage(), fileName);
                user.setImage(fileName);
            } catch (IOException e) {

            }
        }
    }
}
