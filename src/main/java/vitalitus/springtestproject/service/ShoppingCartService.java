package vitalitus.springtestproject.service;

import vitalitus.springtestproject.dto.cart.AddToCartRequestDto;
import vitalitus.springtestproject.dto.cart.ShoppingCartDto;
import vitalitus.springtestproject.dto.cart.UpdateCartItemRequestDto;
import vitalitus.springtestproject.model.User;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart(Long userId);

    ShoppingCartDto addToCart(Long userId, AddToCartRequestDto requestDto);

    ShoppingCartDto updateCartItem(Long userId, Long cartItemId,
                                   UpdateCartItemRequestDto requestDto);

    void removeCartItem(Long userId, Long cartItemId);

    void registerNewShoppingCart(User user);
}
