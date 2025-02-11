package kr.kookmin.jeongo3.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    DUPLICATED_USER_ID(HttpStatus.BAD_REQUEST, "유저 아이디가 중복됩니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 확인하세요."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    POSTLIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요을 찾을 수 없습니다."),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    LOGIN_NOT_FOUND(HttpStatus.NOT_FOUND, "로그인 하지 않은 상태입니다."), // 이거 수정하기
    DISC_NOT_FOUND(HttpStatus.NOT_FOUND, "DISC 테스트를 먼저 진행해주세요."),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "DISC 테스트를 먼저 진행해주세요."),

    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    WRONG_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),

    NOT_FOUND(HttpStatus.NOT_FOUND, "잘못된 접근입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    BAD_REQUEST_DISC(HttpStatus.BAD_REQUEST, "DISC를 다시 진행해주세요."),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 측 기술 오류"),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "왜 에러일까?");

    private HttpStatus httpStatus;
    private String message;
}
