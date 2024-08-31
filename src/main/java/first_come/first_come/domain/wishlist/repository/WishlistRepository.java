package first_come.first_come.domain.wishlist.repository;

import first_come.first_come.domain.user.entity.User;
import first_come.first_come.domain.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUser(User user);
}

