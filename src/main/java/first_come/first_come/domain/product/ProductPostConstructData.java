//package first_come.first_come.domain.product;
//
//import first_come.first_come.domain.product.entity.Product;
//import first_come.first_come.domain.product.repository.ProductRepository;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//
//// DB 최초 실행시에만 주석 풀고 실행
//@Component
//@RequiredArgsConstructor
//public class ProductPostConstructData {
//
//    private final ProductRepository productRepository;
//
//    @PostConstruct
//    public void init() {
//        // Sample products
//        Product product1 = new Product("Product 1", 10000);
//        Product product2 = new Product("Product 2", 20000);
//        Product product3 = new Product("Product 3", 30000);
//        Product product4 = new Product("Product 4", 40000);
//        Product product5 = new Product("Product 5", 50000);
//
//        // Save products to the repository
//        productRepository.save(product1);
//        productRepository.save(product2);
//        productRepository.save(product3);
//        productRepository.save(product4);
//        productRepository.save(product5);
//
//        System.out.println("Product data initialized.");
//    }
//}
