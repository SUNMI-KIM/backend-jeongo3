/*package kr.kookmin.jeongo3.Exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ExceptionDto exceptionDto = new ExceptionDto(ErrorCode.ACCESS_DENIED, ErrorCode.ACCESS_DENIED.getMessage());
        setResponse(response, exceptionDto);
    }

    private void setResponse(HttpServletResponse response, ExceptionDto exceptionDto) throws IOException {
        String result = objectMapper.writeValueAsString(exceptionDto);
        response.setStatus(403);
        response.getWriter().write(result);
    }
}*/
