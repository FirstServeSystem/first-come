package first_come.first_come.domain.wishlist.service;

import first_come.first_come.domain.product.entity.Product;
import first_come.first_come.domain.product.repository.ProductRepository;
import first_come.first_come.domain.user.entity.User;
import first_come.first_come.domain.user.repository.UserRepository;
import first_come.first_come.domain.wishlist.dto.*;
import first_come.first_come.domain.wishlist.entity.Wishlist;
import first_come.first_come.domain.wishlist.entity.WishlistItem;
import first_come.first_come.domain.wishlist.repository.WishlistItemRepository;
import first_come.first_come.domain.wishlist.repository.WishlistRepository;
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
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WishlistItemRepository wishlistItemRepository;

    @Override
    @Transactional
    public CreateWishlistResponseDto createWishlist(UserDetailsImpl userDetails,
                                                    CreateWishlistRequestDto requestDto) {

        User saveUser = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재 하지 않는 회원입니다.")
        );

        Wishlist wishlist = Wishlist.of(saveUser, requestDto);
        wishlistRepository.save(wishlist);

        return CreateWishlistResponseDto.builder()
                .wishlistId(wishlist.getId())
                .wishlistName(wishlist.getWishlistName())
                .build();
    }

    @Override
    public GetWishlistResponseDto getWishlist(UserDetailsImpl userDetails, Long wishlistId) {
        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재 하지 않는 회원입니다.")
        );

        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 위시리스트입니다.")
        );

        List<WishlistItemDto> wishlistItemDtos = wishlist.getWishlistItems().stream()
                .map(WishlistItemDto::new)
                .toList();

        return GetWishlistResponseDto.builder()
                .wishlistId(wishlist.getId())
                .wishlistName(wishlist.getWishlistName())
                .wishlistItems(wishlistItemDtos)
                .build();

    }


    @Override
    public List<WishlistResponseDto> findAllWishlist(UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재 하지 않는 회원입니다.")
        );

        return wishlistRepository.findByUser(user).stream().map(WishlistResponseDto::new).toList();

    }

    @Override
    @Transactional
    public WishlistResponseDto addWishlistItem(Long wishlistId, UserDetailsImpl userDetails, AddWishlistItemRequestDto requestDto) {

        User user = userRepository.findByEmail(userDetails.getUser().getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재 하지 않는 회원입니다.")
        );

        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 위시리스트입니다.")
        );

        log.info(String.valueOf(requestDto.getProductId()));
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 상품입니다.")
        );

        WishlistItem wishlistItem = WishlistItem.builder()
                .wishlist(wishlist)
                .product(product)
                .build();

        wishlistItemRepository.save(wishlistItem);

        return WishlistResponseDto.builder()
                .wishlistId(wishlist.getId())
                .wishlistName(wishlist.getWishlistName())
                .build();

    }

    @Override
    @Transactional
    public void deleteWishlistItem(Long wishlistItemId) {
        wishlistItemRepository.deleteById(wishlistItemId);
    }


    @Override
    @Transactional
    public void deleteWishlist(Long wishlistId) {
        wishlistRepository.deleteById(wishlistId);
    }
}
