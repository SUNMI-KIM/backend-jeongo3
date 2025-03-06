package kr.kookmin.jeongo3.Orders;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.kookmin.jeongo3.Exception.ExceptionDto;
import kr.kookmin.jeongo3.Orders.Dto.OrdersMapping;
import kr.kookmin.jeongo3.Orders.Dto.RequestOrdersDto;
import kr.kookmin.jeongo3.Common.Response;
import kr.kookmin.jeongo3.User.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@RestController
@RequiredArgsConstructor
@Tag(name = "Orders", description = "주문 관련 API")
public class OrdersController {

    private final OrdersService ordersService;

    @Operation(summary = "주문 하기", description = "주문 서비스 주문 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "유저나 상품을 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "400", description = "유저의 포인트가 부족함", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @PostMapping("/orders")
    public ResponseEntity<Response<Object>> ordersUpload(@RequestBody RequestOrdersDto requestOrdersDto, Authentication authentication) {
        ordersService.saveOrders(requestOrdersDto, ((CustomUserDetails) authentication.getPrincipal()).getUser());
        Response response = Response.builder().message("주문 완료").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "주문 내역 확인", description = "주문 서비스 주문 내역 확인 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없음", content = @Content(
                    schema = @Schema(implementation = ExceptionDto.class)))
    })
    @GetMapping("/orders")
    public ResponseEntity<Response<List<OrdersMapping>>> ordersList(Authentication authentication) {
        List<OrdersMapping> orderList = ordersService.findAllOrders(((CustomUserDetails) authentication.getPrincipal()).getUser());
        Response response = Response.builder().message("주문 내역").data(orderList).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
