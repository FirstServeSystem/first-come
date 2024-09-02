package first_come.first_come.domain.order.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderRequestDto {

    private String address;
    private List<OrderItemDto> OrderItemDto = new ArrayList<>();


}
