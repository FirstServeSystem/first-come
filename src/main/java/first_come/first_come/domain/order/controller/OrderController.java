package first_come.first_come.domain.order.controller;

import first_come.first_come.domain.order.dto.OrderRequestDto;
import first_come.first_come.domain.order.dto.OrderResponseDto;
import first_come.first_come.domain.order.service.OrderServiceImpl;
import first_come.first_come.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderServiceImpl orderService;

    // 주문하기(주문 생성)
    @PostMapping
    public ResponseEntity<OrderResponseDto> creatOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                       @RequestBody OrderRequestDto requestDto) {

        OrderResponseDto responseDto = orderService.createOrder(userDetails, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 주문 목록 전체 조회
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<OrderResponseDto> responseDtos = orderService.getOrders(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    // 특정 주문 선택 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> findOrder(@PathVariable(name = "orderId") Long orderId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {

        OrderResponseDto responseDto = orderService.findOrder(orderId, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

//    @DeleteMapping("/{orderId}")
//    public ResponseEntity<Void> cancelOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                                            @PathVariable(name = "orderId") Long orderId) {
//
//
//
//    }




}
