package kr.kookmin.jeongo3.DISC;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kookmin.jeongo3.DISC.Dto.DISCRequestDto;
import kr.kookmin.jeongo3.DISC.Dto.DISCResponseDto;
import kr.kookmin.jeongo3.Exception.ExceptionDto;
import kr.kookmin.jeongo3.Common.Response;
import kr.kookmin.jeongo3.User.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "DISC", description = "DISC 관련 API")
public class DISCController {

    private final DISCService discService;

    @Operation(summary = "DISC 결과", description = "DISC 테스트 결과 저장")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "400", description = "DISC 테스트 재시도 요망", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @PostMapping("/DISC")
    public ResponseEntity<Response<DISCResponseDto>> DISCUpload(Authentication authentication, @RequestBody DISCRequestDto discRequestDto) {
        DISCResponseDto discResponseDto = discService.saveDISC(discRequestDto ,((CustomUserDetails) authentication.getPrincipal()).getUser());
        Response response = Response.builder().message("DISC 테스트 결과 저장").data(discResponseDto).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "DISC 상세", description = "DISC 테스트 결과 불러오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없거나, DISC 테스트를 아직 하지 않음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class))),
    })
    @GetMapping("/DISC")
    public ResponseEntity<Response<DISCResponseDto>> DISCDetail(Authentication authentication) {
        DISCResponseDto discResponseDto = discService.findDISC(((CustomUserDetails) authentication.getPrincipal()).getUser());
        Response response = Response.builder().message("DISC 테스트 결과").data(discResponseDto).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "DISC 유저 수", description = "모든 DISC 테스트 개수 불러오기")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @GetMapping("/DISC-headcount")
    public ResponseEntity<Response<Long>> DISCCount() {
        Response response = Response.builder().message("DISC 테스트 인원").data(discService.countDISC()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
