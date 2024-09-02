package first_come.first_come.domain.order.repository;

import first_come.first_come.domain.order.entity.Order;
import first_come.first_come.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
