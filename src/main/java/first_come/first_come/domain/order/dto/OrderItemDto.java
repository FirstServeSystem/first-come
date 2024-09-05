package first_come.first_come.domain.order.dto;

import first_come.first_come.domain.order.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long productId;
    private int quantity;
    private int price;
    private int totalAmount;


    public OrderItemDto(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getId();
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
        this.totalAmount = this.quantity * this.price;
    }
}
