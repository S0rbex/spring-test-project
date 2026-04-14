package vitalitus.springtestproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitalitus.springtestproject.dto.cart.AddToCartRequestDto;
import vitalitus.springtestproject.dto.cart.ShoppingCartDto;
import vitalitus.springtestproject.dto.cart.UpdateCartItemRequestDto;
import vitalitus.springtestproject.exception.EntityNotFoundException;
import vitalitus.springtestproject.exception.InvalidCartOperationExcpetion;
import vitalitus.springtestproject.mapper.ShoppingCartMapper;
import vitalitus.springtestproject.model.Book;
import vitalitus.springtestproject.model.CartItem;
import vitalitus.springtestproject.model.ShoppingCart;
import vitalitus.springtestproject.repository.book.BookRepository;
import vitalitus.springtestproject.repository.cart.CartItemRepository;
import vitalitus.springtestproject.repository.cart.ShoppingCartRepository;
import vitalitus.springtestproject.service.ShoppingCartService;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    
    @Override
    public ShoppingCartDto getShoppingCart(Long userId) {
        return shoppingCartMapper.toDto(shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find cart "
                        + "for user with id: " + userId)));
    }

    @Override
    public ShoppingCartDto addToCart(Long userId, AddToCartRequestDto requestDto) {
        ShoppingCart cartEntity = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find cart for user with id: "
                        + userId));
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id "
                        + requestDto.getBookId()));

        cartEntity.getCartItems().stream()
                .filter(item -> item.getBook().getId().equals(requestDto.getBookId()))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + requestDto.getQuantity()),
                        () -> {
                            CartItem newItem = new CartItem();
                            newItem.setShoppingCart(cartEntity);
                            newItem.setBook(book);
                            newItem.setQuantity(requestDto.getQuantity());
                            cartItemRepository.save(newItem);
                        });
        return shoppingCartMapper.toDto(cartEntity);
    }

    @Override
    public ShoppingCartDto updateCartItem(Long userId, Long cartItemId,
                                          UpdateCartItemRequestDto requestDto) {
        ShoppingCart cartEntity = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find cart for user with id: "
                        + userId));
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        cartItem.setQuantity(requestDto.getQuantity());
        return shoppingCartMapper.toDto(cartEntity);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) {
        ShoppingCart cartEntity = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find cart for user with id: "
                        + userId));
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        if (cartItem.getShoppingCart().getId().equals(cartEntity.getId())) {
            cartItemRepository.delete(cartItem);
        }
    }
}
