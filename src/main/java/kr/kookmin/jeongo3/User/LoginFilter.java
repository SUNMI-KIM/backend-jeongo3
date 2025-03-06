package kr.kookmin.jeongo3.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kookmin.jeongo3.Exception.ErrorCode;
import kr.kookmin.jeongo3.Security.Jwt.Dto.TokenDto;
import kr.kookmin.jeongo3.Security.Jwt.JwtProvider;
import kr.kookmin.jeongo3.Security.Jwt.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoginFilter(JwtProvider jwtProvider, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
        User user = customUserDetails.getUser();
        String accessToken = jwtProvider.createAccessToken(user.getLoginId(), user.getUserRole());
        String refreshToken = jwtProvider.createRefreshToken();
        jwtService.addRefreshToken(user, refreshToken);

        TokenDto tokenDto = new TokenDto(accessToken, refreshToken);

        response.addHeader("Authorization", accessToken);
        response.addHeader("Refresh-token", refreshToken);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(tokenDto));
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 403 상태 설정
        response.setContentType("application/json;charset=UTF-8");

        String jsonResponse = String.format("""
{
    "errorCode": "%s",
    "message": "%s"
}
""", ErrorCode.ACCESS_DENIED.name(), ErrorCode.ACCESS_DENIED.getMessage());

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
