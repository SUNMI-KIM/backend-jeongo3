package kr.kookmin.jeongo3.PostLike;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kookmin.jeongo3.Exception.ExceptionDto;
import kr.kookmin.jeongo3.Common.Response;
import kr.kookmin.jeongo3.User.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "PostLike", description = "좋아요 관련 API")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Operation(summary = "좋아요 누르기", description = "좋아요 서비스 저장 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없거나 게시글을 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @PostMapping("/post-like")
    public ResponseEntity<Response<Object>> postLikeSave(@RequestParam String postId, Authentication authentication) {
        postLikeService.savePostLike(postId, ((CustomUserDetails) authentication.getPrincipal()).getUser());
        Response response = Response.builder().message("좋아요 저장").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "좋아요 다시 누르기", description = "좋아요 서비스 삭제 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "좋아요를 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "400", description = "자신의 좋아요만 취소 가능", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @DeleteMapping("/post-like")
    public ResponseEntity<Response<Object>> postLikeDelete(@RequestParam String postId, Authentication authentication) {
        postLikeService.deletePostLike(postId, ((CustomUserDetails) authentication.getPrincipal()).getUser());
        Response response = Response.builder().message("좋아요 삭제").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
