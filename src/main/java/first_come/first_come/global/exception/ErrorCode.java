package first_come.first_come.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    ORDER_NOT_FOUND(404, "Order not found"),
    ORDERITEM_NOT_FOUND(404, "Orderitem not found"),
    WISHLIST_NOT_FOUND(404, "Wishlist not found"),
    WISHLIST_ITEM_NOT_FOUND(404, "Wishlist item not found");


    private final int status;
    private final String message;

}
