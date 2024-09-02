package first_come.first_come.domain.order.entity;

import first_come.first_come.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String address;

    @CreatedDate
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate orderDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Setter
    @Column(nullable = false)
    private int totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order (User user, String address, OrderStatus status) {
        this.user = user;
        this.address = address;
        this.status = status;
        this.orderDate = LocalDate.now();
        this.orderItems = new ArrayList<>();
        this.totalAmount = calculateTotalAmount();
    }

    public int calculateTotalAmount() {
        assert orderItems != null;
        return orderItems.stream()
                .mapToInt(OrderItem::getPrice)
                .sum();
    }

    public void addOrderItem(OrderItem orderItem) {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
        this.totalAmount = calculateTotalAmount();
    }
}
