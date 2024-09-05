package first_come.first_come.domain.wishlist.controller;

import first_come.first_come.domain.user.entity.User;
import first_come.first_come.domain.wishlist.dto.*;
import first_come.first_come.domain.wishlist.service.WishlistServiceImpl;
import first_come.first_come.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistServiceImpl wishlistService;

    // 위시리스트 생성
    @PostMapping
    public ResponseEntity<CreateWishlistResponseDto> createWishlist(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                    @RequestBody CreateWishlistRequestDto requestDto) {
        User user = userDetails.getUser();
        CreateWishlistResponseDto responseDto = wishlistService.createWishlist(userDetails, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 특정 위시리스트 목록 조회
    @GetMapping("/{wishlistId}")
    public ResponseEntity<GetWishlistResponseDto> getWishlist(@PathVariable(name = "wishlistId") Long wishlistId,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        GetWishlistResponseDto responseDto = wishlistService.getWishlist(userDetails, wishlistId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 위시리스트 목록 전체 조회
    @GetMapping
    public ResponseEntity<List<WishlistResponseDto>> findAllWishlist(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<WishlistResponseDto> responseDtos = wishlistService.findAllWishlist(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    // 위시리스트에 상품 추가
    @PostMapping("/{wishlistId}")
    public ResponseEntity<WishlistResponseDto> addWishlistItem(@PathVariable(name = "wishlistId") Long wishlistId,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                               @RequestBody AddWishlistItemRequestDto requestDto) {

        WishlistResponseDto responseDto = wishlistService.addWishlistItem(wishlistId, userDetails, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    }

    // 위시리스트 상품 삭제
    @DeleteMapping("/wishlistItem/{wishlistItemId}")
    public ResponseEntity<Void> deleteWishlistItem(@PathVariable(name = "wishlistItemId") Long wishlistItemId) {

        wishlistService.deleteWishlistItem(wishlistItemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    // 위시리스트 삭제
    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable(name = "wishlistId") Long wishlistId) {

        wishlistService.deleteWishlist(wishlistId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
