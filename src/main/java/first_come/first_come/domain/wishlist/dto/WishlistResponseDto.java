package first_come.first_come.domain.wishlist.dto;

import first_come.first_come.domain.wishlist.entity.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistResponseDto {

    private Long wishlistId;
    private String wishlistName;
    private List<WishlistItemDto> wishlistItems = new ArrayList<>();

    @Builder
    public WishlistResponseDto(Wishlist wishlist) {
        this.wishlistId = wishlist.getId();
        this.wishlistName = wishlist.getWishlistName();
        this.wishlistItems = wishlist.getWishlistItems().stream().map(WishlistItemDto::new).toList();
    }
}
