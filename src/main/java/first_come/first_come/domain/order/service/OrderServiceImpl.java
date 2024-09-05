package first_come.first_come.domain.order.service;

import first_come.first_come.domain.order.dto.OrderItemDto;
import first_come.first_come.domain.order.dto.OrderRequestDto;
import first_come.first_come.domain.order.dto.OrderResponseDto;
import first_come.first_come.domain.order.entity.Order;
import first_come.first_come.domain.order.entity.OrderItem;
import first_come.first_come.domain.order.entity.OrderStatus;
import first_come.first_come.domain.order.repository.OrderRepository;
import first_come.first_come.domain.product.entity.Product;
import first_come.first_come.domain.product.repository.ProductRepository;
import first_come.first_come.domain.user.entity.User;
import first_come.first_come.domain.user.repository.UserRepository;
import first_come.first_come.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    // 주문 생성. 배송지, 주문할 물건들을 건네 받아서 바로 주문 단계로 진입.
    @Transactional
    public OrderResponseDto createOrder(UserDetailsImpl userDetails, OrderRequestDto requestDto) {

        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        );


        Order order = Order.builder()
                .user(user)
                .address(requestDto.getAddress())
                .status(OrderStatus.PENDING)
                .build();

        int totalAmount = 0;

        log.info(requestDto.getOrderItemDto().toString());
        for (OrderItemDto itemDto : requestDto.getOrderItemDto()) {
            Product product = productRepository.findById(itemDto.getProductId()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 상품입니다.")
            );

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .price(itemDto.getPrice())
                    .order(order)
                    .build();

            order.addOrderItem(orderItem);
            totalAmount += orderItem.getPrice() * orderItem.getQuantity();

        }

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDto(savedOrder);

    }


    public List<OrderResponseDto> getOrders(UserDetailsImpl userDetails) {

        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        );

        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream().map(OrderResponseDto::new).toList();

    }

    public OrderResponseDto findOrder(Long orderId, UserDetailsImpl userDetails) {

        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        );

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 주문 내역입니다")
        );

        return new OrderResponseDto(order);
    }




}


