package kr.kookmin.jeongo3.Exception;

import lombok.Getter;

@Getter
public class MyException extends RuntimeException {

    private final String result = "ERROR";
    private ErrorCode errorCode;
    private String message;

    public MyException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

}
