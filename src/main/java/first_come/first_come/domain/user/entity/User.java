package first_come.first_come.domain.user.entity;

import first_come.first_come.domain.order.entity.Order;
import first_come.first_come.domain.wishlist.entity.Wishlist;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Setter
    @Column(nullable = false)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Setter
    private boolean isVerified;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Wishlist> wishlists = new ArrayList<>();


    public static User of(String email, String password, String name, String phone, String address) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .address(address)
                .isVerified(false)
                .role(UserRoleEnum.USER)
                .build();
    }

}
