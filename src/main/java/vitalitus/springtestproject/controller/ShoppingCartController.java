package vitalitus.springtestproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vitalitus.springtestproject.dto.cart.AddToCartRequestDto;
import vitalitus.springtestproject.dto.cart.ShoppingCartDto;
import vitalitus.springtestproject.dto.cart.UpdateCartItemRequestDto;
import vitalitus.springtestproject.model.User;
import vitalitus.springtestproject.service.ShoppingCartService;

@Tag(name = "Shopping cart management", description = "Endpoints for managing user's shopping cart")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get user cart", description = "Retrieve user's shopping cart")
    public ShoppingCartDto getShoppingCart(@AuthenticationPrincipal User user) {
        return shoppingCartService.getShoppingCart(user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @Operation(summary = "Add book to cart", description = "Add a new book to the shopping cart")
    public ShoppingCartDto addToCart(@AuthenticationPrincipal User user,
                                     @RequestBody @Valid AddToCartRequestDto requestDto) {
        return shoppingCartService.addToCart(user.getId(), requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/items/{cartItemId}")
    @Operation(summary = "Update item quantity", description = "Update quantity of a book in the shopping cart")
    public ShoppingCartDto updateCartItem(@AuthenticationPrincipal User user,
                                          @PathVariable Long cartItemId,
                                          @RequestBody @Valid UpdateCartItemRequestDto requestDto) {
        return shoppingCartService.updateCartItem(user.getId(), cartItemId, requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/items/{cartItemId}")
    @Operation(summary = "Delete item", description = "Remove a book from the shopping cart")
    public void removeCartItem(@AuthenticationPrincipal User user,
                               @PathVariable Long cartItemId) {
        shoppingCartService.removeCartItem(user.getId(), cartItemId);
    }
}
