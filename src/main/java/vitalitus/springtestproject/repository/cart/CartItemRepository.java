package vitalitus.springtestproject.repository.cart;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import vitalitus.springtestproject.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndShoppingCartId(Long id, Long shoppingCartId);
}
