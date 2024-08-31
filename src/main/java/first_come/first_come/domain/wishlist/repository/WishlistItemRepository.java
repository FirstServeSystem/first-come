package first_come.first_come.domain.wishlist.repository;

import first_come.first_come.domain.wishlist.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
}
