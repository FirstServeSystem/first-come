package first_come.first_come.domain.product.Controller;

import first_come.first_come.domain.product.dto.ProductResponseDto;
import first_come.first_come.domain.product.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductServiceImpl productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProducts() {

        List<ProductResponseDto> productlist = productService.getProducts();
        return ResponseEntity.status(HttpStatus.OK).body(productlist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable(name = "id") Long id) {

        ProductResponseDto productDetail = productService.getProductDetail(id);
        return ResponseEntity.status(HttpStatus.OK).body(productDetail);
    }


}
