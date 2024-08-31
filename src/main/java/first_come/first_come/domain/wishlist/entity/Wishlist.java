package first_come.first_come.domain.wishlist.entity;

import first_come.first_come.domain.user.entity.User;
import first_come.first_come.domain.wishlist.dto.CreateWishlistRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(nullable = false)
    private String wishlistName;

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WishlistItem> wishlistItems = new ArrayList<>();

    public static Wishlist of(User user, CreateWishlistRequestDto requestDto) {
        return Wishlist.builder()
                .user(user)
                .wishlistName(requestDto.getWishlistName())
                .build();

    }
}