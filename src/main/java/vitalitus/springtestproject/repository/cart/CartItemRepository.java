package vitalitus.springtestproject.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import vitalitus.springtestproject.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
