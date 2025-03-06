package kr.kookmin.jeongo3.Security.Jwt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kookmin.jeongo3.Exception.ExceptionDto;
import kr.kookmin.jeongo3.Common.Response;
import kr.kookmin.jeongo3.Security.Jwt.Dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "JWT", description = "토큰 재발급 API")
public class JwtController {

    private final JwtService jwtService;

    @Operation(summary = "토큰 재발급", description = "토큰 재발급 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "토큰의 형식에 문제가 있거나 refresh token의 기간이 만료됨", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "400", description = "유저를 찾을 수 없거나, 로그인 상태가 아니거나, 토큰을 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class))),
    })
    @PostMapping("/refresh")
    public ResponseEntity<Response<TokenDto>> refresh(@RequestParam String accessToken,
                                            @RequestHeader("refreshToken") String refreshToken) {
        TokenDto tokenDto = jwtService.updateRefreshToken(accessToken, refreshToken);
        Response response = Response.builder().message("토큰 재발급 성공").data(tokenDto).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
