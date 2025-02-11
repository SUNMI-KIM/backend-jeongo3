package kr.kookmin.jeongo3.Swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "JWT 테스트 명세서",
        description = "JWT accessToken, refreshToken 테스트 API 명세서",
        version = "v1"))
@Configuration
public class SwaggerConfig {
}
