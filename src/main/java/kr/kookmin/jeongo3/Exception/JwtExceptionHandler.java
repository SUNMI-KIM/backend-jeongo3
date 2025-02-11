package kr.kookmin.jeongo3.Exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static java.time.LocalDateTime.now;

@Slf4j
@RestControllerAdvice
public class JwtExceptionHandler {

    @ExceptionHandler({SignatureException.class, MalformedJwtException.class, StringIndexOutOfBoundsException.class})
    public ResponseEntity<ErrorResponse> JwtInvalidException() {
        ErrorCode errorCode = ErrorCode.WRONG_TOKEN;
        return ResponseEntity.status(errorCode.getHttpStatus()).body(makeErrorResponse(errorCode));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> JwtExpiredException() {
        ErrorCode errorCode = ErrorCode.EXPIRED_TOKEN;
        return ResponseEntity.status(errorCode.getHttpStatus()).body(makeErrorResponse(errorCode));
    }

    /*@ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ErrorResponse> JwtNullPointerException() {
        ErrorCode errorCode = ErrorCode.NULL_TOKEN_ERROR;
        return ResponseEntity.status(errorCode.getHttpStatus()).body(makeErrorResponse(errorCode));
    }*/

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return ErrorResponse.builder()
                .timeStamp(now())
                .status(errorCode.getHttpStatus().value())
                .error("401")
                .message(errorCode.getMessage())
                .path(request.getRequestURI())
                .build();
    }

}
