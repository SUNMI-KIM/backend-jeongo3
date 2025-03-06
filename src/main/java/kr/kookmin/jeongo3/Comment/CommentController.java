package kr.kookmin.jeongo3.Comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kookmin.jeongo3.Comment.Dto.RequestCommentDto;
import kr.kookmin.jeongo3.Exception.ExceptionDto;
import kr.kookmin.jeongo3.Common.Response;
import kr.kookmin.jeongo3.User.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Comment", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글", description = "댓글 서비스 댓글 저장 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "유저가 없거나 게시글을 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @PostMapping("/comment")
    public ResponseEntity<Response<Object>> commentUpload(@RequestBody RequestCommentDto requestCommentDto, Authentication authentication) {
        commentService.saveComment(requestCommentDto, ((CustomUserDetails) authentication.getPrincipal()).getUser());
        Response response = Response.builder().message("댓글 저장").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "댓글", description = "댓글 서비스 댓글 삭제 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "자신의 댓글만 삭제 가능", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @DeleteMapping("/comment")
    public ResponseEntity<Response<Object>> commentDelete(@RequestParam String commentId, Authentication authentication) {
        commentService.deleteComment(commentId, ((CustomUserDetails) authentication.getPrincipal()).getUser());
        Response response = Response.builder().message("댓글 삭제").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
