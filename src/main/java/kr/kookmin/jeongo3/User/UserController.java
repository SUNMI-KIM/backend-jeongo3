package kr.kookmin.jeongo3.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kookmin.jeongo3.Exception.ExceptionDto;
import kr.kookmin.jeongo3.Common.Response;
import kr.kookmin.jeongo3.Security.Jwt.Dto.TokenDto;
import kr.kookmin.jeongo3.User.Dto.RequestUserDto;
import kr.kookmin.jeongo3.User.Dto.RequestUserUpdateDto;
import kr.kookmin.jeongo3.User.Dto.ResponseUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 API")
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "유저 서비스 회원 가입 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "유저 아이디 중복으로 인한 실패", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @PostMapping("/user")
    public ResponseEntity<Response<Object>> userRegister(@RequestBody RequestUserDto requestUserDto) {
        userService.saveUser(requestUserDto);
        Response response = Response.builder().message("회원가입 성공").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "아이디 중복 체크", description = "유저 서비스 아이디 중복 확인 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "유저 아이디 중복으로 인한 실패", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @GetMapping("/user/validation")
    public ResponseEntity<Response<Boolean>> userValidate(@RequestParam String loginId) {
        Response response = Response.builder().message("중복 아이디 없음").data(userService.validateUser(loginId)).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*@Operation(summary = "로그인", description = "유저 서비스 로그인 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "400", description = "유저 패스워드가 일치하지 않음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @GetMapping("/login")
    public ResponseEntity<Response<TokenDto>> userLogin(@RequestParam String loginId, @RequestParam String password) {
        TokenDto tokenDto = userService.loginUser(loginId, password);
        Response response = Response.builder().message("로그인 성공").data(tokenDto).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }*/


    @Operation(summary = "마이페이지", description = "유저 마이페이지 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @GetMapping("/user")
    public ResponseEntity<Response<ResponseUserDto>> userDetails(Authentication authentication) {
        ResponseUserDto responseUserDto = userService.findByIdUser(((CustomUserDetails) authentication.getPrincipal()).getUser());
        Response response = Response.builder().message("유저 상세 정보").data(responseUserDto).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users") // 디버깅용
    public List<User> userList() { // 얘는 디비 보는 용도
        return userService.findAllUser();
    }

    @Operation(summary = "신고", description = "유저 서비스 신고 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @PatchMapping("/user/report")
    public ResponseEntity<Response<Integer>> userReport(@RequestParam String id) {
        int report = (int) userService.reportUser(id);
        Response response = Response.builder().message("유저 신고").data(report).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "수정", description = "유저 서비스 수정 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @PutMapping("/user")
    public ResponseEntity<Response<Object>> userUpdate(Authentication authentication, @RequestBody RequestUserUpdateDto requestUserUpdateDto) {
        userService.updateUser(((CustomUserDetails) authentication.getPrincipal()).getUser(), requestUserUpdateDto);
        Response response = Response.builder().message("유저 수정 성공").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
