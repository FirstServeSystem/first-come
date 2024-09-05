package first_come.first_come.domain.product.service;

import first_come.first_come.domain.product.dto.ProductResponseDto;
import java.util.List;

public interface ProductService {

    List<ProductResponseDto> getProducts();

    ProductResponseDto getProductDetail(Long id);
}
