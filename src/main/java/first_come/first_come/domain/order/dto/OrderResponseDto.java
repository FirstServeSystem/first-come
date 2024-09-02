package first_come.first_come.domain.order.dto;

import first_come.first_come.domain.order.entity.Order;
import first_come.first_come.domain.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Long orderId;
    private String address;
    private LocalDate orderDate;
    private OrderStatus status;
    private int totalAmount;
    private List<OrderItemDto> orderItems;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.address = order.getAddress();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
        this.totalAmount = order.getTotalAmount();
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemDto::new)
                .toList();

    }

}
