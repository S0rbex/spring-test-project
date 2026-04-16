package vitalitus.springtestproject.dto.cart;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> cartItems;
}
