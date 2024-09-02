package first_come.first_come.domain.order.entity;

public enum OrderStatus {
    PENDING,    // 주문 접수 중
    SHIPPED,    // 배송 중
    DELIVERED,  // 배송 완료
    CANCELLED   // 주문 취소
}