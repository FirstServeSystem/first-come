package first_come.first_come.domain.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponseDto {

    private Long id;
    private String name;
    private int price;

}
