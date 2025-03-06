package kr.kookmin.jeongo3.Common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Response<T> {

    private String message;
    private T data;

    public Response(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
