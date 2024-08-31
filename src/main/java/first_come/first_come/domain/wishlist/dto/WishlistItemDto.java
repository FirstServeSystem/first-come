package first_come.first_come.domain.wishlist.dto;

import first_come.first_come.domain.wishlist.entity.WishlistItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItemDto {

    private Long wishlistItemId;
    private Long productId;
    private String productName;
    private int price;

    public WishlistItemDto(WishlistItem wishlistItem) {
        this.wishlistItemId = wishlistItem.getId();
        this.productId = wishlistItem.getProduct().getId();
        this.productName = wishlistItem.getProduct().getName();
        this.price = wishlistItem.getProduct().getPrice();
    }
}
