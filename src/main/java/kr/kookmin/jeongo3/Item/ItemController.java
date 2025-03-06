package kr.kookmin.jeongo3.Item;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kookmin.jeongo3.Exception.ExceptionDto;
import kr.kookmin.jeongo3.Item.Dto.RequestItemDto;
import kr.kookmin.jeongo3.Item.Dto.RequestItemModifyDto;
import kr.kookmin.jeongo3.Item.Dto.ResponseItemDto;
import kr.kookmin.jeongo3.Common.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@RestController
@RequiredArgsConstructor
@Tag(name = "Item", description = "상품 관련 API")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "상품 올리기", description = "상품 서비스 저장 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @PostMapping("/item")
    public ResponseEntity<Response<Object>> itemUpload(@RequestBody RequestItemDto requestItemDto) {
        itemService.saveItem(requestItemDto);
        Response response = Response.builder().message("상품 저장").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "상품 불러오기", description = "상품 서비스 불러오기 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @GetMapping("/item")
    public ResponseEntity<Response<ResponseItemDto>> itemDetail(@RequestParam String itemId) {
        ResponseItemDto responseItemDto = itemService.findItem(itemId);
        Response response = Response.builder().message("상품 상세 내용").data(responseItemDto).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "모든 상품 불러오기", description = "상품 서비스 모두 불러오기 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    })
    @GetMapping("/items")
    public ResponseEntity<Response<List<ResponseItemDto>>> itemList() {
        List<ResponseItemDto> itemList = itemService.findAllItem();
        Response response = Response.builder().message("게시글 수정").data(itemList).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "상품 삭제", description = "상품 서비스 삭제 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    })
    @DeleteMapping("/item")
    public ResponseEntity<Response<Object>> itemDelete(@RequestParam String itemId) {
        itemService.deleteItem(itemId);
        Response response = Response.builder().message("상품 삭제").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "상품 수정", description = "상품 서비스 수정 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @PutMapping("/item")
    public ResponseEntity<Response<Object>> itemModify(@RequestBody RequestItemModifyDto requestItemModifyDto) {
        itemService.ModifyItem(requestItemModifyDto);
        Response response = Response.builder().message("상품 수정").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
