package first_come.first_come.domain.wishlist.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetWishlistResponseDto {
    private Long wishlistId;
    private String wishlistName;
    private List<WishlistItemDto> wishlistItems;
}
