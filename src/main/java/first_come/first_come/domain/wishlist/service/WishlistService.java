package first_come.first_come.domain.wishlist.service;

import first_come.first_come.domain.wishlist.dto.*;
import first_come.first_come.security.service.UserDetailsImpl;

import java.util.List;

public interface WishlistService {
    CreateWishlistResponseDto createWishlist(UserDetailsImpl userDetails,
                                             CreateWishlistRequestDto requestDto);

    GetWishlistResponseDto getWishlist(UserDetailsImpl userDetails, Long wishlistId);

    List<WishlistResponseDto> findAllWishlist(UserDetailsImpl userDetails);

    WishlistResponseDto addWishlistItem(Long wishlistId, UserDetailsImpl userDetails, AddWishlistItemRequestDto requestDto);

    void deleteWishlistItem(Long wishlistItemId);

    void deleteWishlist(Long wishlistId);
}
