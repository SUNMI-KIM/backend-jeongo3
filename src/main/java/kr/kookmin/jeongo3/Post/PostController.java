package kr.kookmin.jeongo3.Post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kookmin.jeongo3.Exception.ExceptionDto;
import kr.kookmin.jeongo3.Post.Dto.*;
import kr.kookmin.jeongo3.Common.Response;
import kr.kookmin.jeongo3.Post.Dto.PostMapping;
import kr.kookmin.jeongo3.User.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Post", description = "게시글 관련 API")
public class PostController {

    private final PostService postService;

    @Operation(summary = "글 올리기", description = "게시글 서비스 저장 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "권한이 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @org.springframework.web.bind.annotation.PostMapping("/post")
    public ResponseEntity<Response<String>> postUpload(@RequestBody RequestPostDto requestPostDto, Authentication authentication) {
        String postId = postService.savePost(requestPostDto, ((CustomUserDetails) authentication.getPrincipal()).getUser());
        Response response = Response.builder().message("게시글 저장").data(postId).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "삭제", description = "게시글 서비스 삭제 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @DeleteMapping("/post")
    public ResponseEntity<Response<Object>> postDelete(@RequestParam String id, Authentication authentication) {
        postService.deletePost(id, ((CustomUserDetails) authentication.getPrincipal()).getUser());
        Response response = Response.builder().message("게시글 삭제").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "수정", description = "게시글 서비스 수정 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "400", description = "자신의 게시글만 수정 가능", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @PatchMapping("/post")
    public ResponseEntity<Response<Object>> postModify(@RequestBody RequestPostDto requestPost, Authentication authentication) {
        postService.updatePost(requestPost, ((CustomUserDetails) authentication.getPrincipal()).getUser());
        Response response = Response.builder().message("게시글 수정").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "게시글 상세", description = "게시글 서비스 상세 불러오기 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @GetMapping("/post")
    public ResponseEntity<Response<ResponsePostDto>> postDetails(@RequestParam String id, Authentication authentication) {
        ResponsePostDto responsePostDto = postService.findPost(id, ((CustomUserDetails) authentication.getPrincipal()).getUser());
        Response response = Response.builder().message("게시글 상세 내용").data(responsePostDto).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "전체 게시글", description = "게시글 서비스 전체 게시글 불러오기 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    })
    @GetMapping("/posts")
    public ResponseEntity<Response<List<ResponseAllPostDto>>> postList(@RequestParam PostType postType, @PageableDefault(size = 10)Pageable pageable) { // page = 10 이런 식으로 보내주기
        List<ResponseAllPostDto> postList = postService.findAllPost(postType, pageable);
        Response response = Response.builder().message("모든 게시글").data(postList).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "핫 게시글", description = "게시글 서비스 핫 게시글 불러오기 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    })
    @GetMapping("/hot-post")
    public ResponseEntity<Response<ResponseHotPostDto>> postHotFind(PostType postType) { // 함수 이름 적당한걸로 바꾸기
        ResponseHotPostDto post = postService.findHotPost(postType);
        Response response = Response.builder().message("핫 게시글").data(post).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
