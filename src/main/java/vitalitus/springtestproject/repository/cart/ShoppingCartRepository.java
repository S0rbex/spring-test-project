package vitalitus.springtestproject.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vitalitus.springtestproject.model.ShoppingCart;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT sc FROM ShoppingCart sc LEFT JOIN FETCH sc.cartItems i LEFT JOIN FETCH i.book WHERE sc.user.id = :userId")
    Optional<ShoppingCart> findByUserId(Long userId);
}
