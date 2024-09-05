package first_come.first_come.domain.wishlist.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateWishlistResponseDto {

    private Long wishlistId;
    private String wishlistName;

}
