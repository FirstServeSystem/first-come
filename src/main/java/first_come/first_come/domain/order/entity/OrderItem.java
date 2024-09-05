package first_come.first_come.domain.order.entity;

import first_come.first_come.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int totalPrice;

    @Builder
    public OrderItem(Product product, int quantity, int price, Order order) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.totalPrice = product.getPrice() * quantity;
    }

}
