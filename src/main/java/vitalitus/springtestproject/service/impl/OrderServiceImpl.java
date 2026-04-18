package vitalitus.springtestproject.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitalitus.springtestproject.dto.order.OrderItemResponseDto;
import vitalitus.springtestproject.dto.order.OrderRequestDto;
import vitalitus.springtestproject.dto.order.OrderResponseDto;
import vitalitus.springtestproject.dto.order.OrderStatusRequestDto;
import vitalitus.springtestproject.exception.EntityNotFoundException;
import vitalitus.springtestproject.mapper.OrderItemMapper;
import vitalitus.springtestproject.mapper.OrderMapper;
import vitalitus.springtestproject.model.CartItem;
import vitalitus.springtestproject.model.Order;
import vitalitus.springtestproject.model.OrderItem;
import vitalitus.springtestproject.model.ShoppingCart;
import vitalitus.springtestproject.model.User;
import vitalitus.springtestproject.repository.cart.ShoppingCartRepository;
import vitalitus.springtestproject.repository.order.OrderItemRepository;
import vitalitus.springtestproject.repository.order.OrderRepository;
import vitalitus.springtestproject.service.OrderService;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    @Override
    public OrderResponseDto placeOrder(User user, OrderRequestDto orderRequestDto) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(
                        () -> new NoSuchElementException("Can't find shopping cart by user id: "
                                + user.getId()));
        if (cart.getCartItems().isEmpty()) {
            throw new OrderProcessingException("Can't place order with empty cart");
        }
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setShippingAddress(orderRequestDto.getShippingAddress());
        order.setOrderDate(LocalDateTime.now());
        Set<OrderItem> orderItems = new HashSet<>();
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
            BigDecimal itemTotal = cartItem
                    .getBook()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total = total.add(itemTotal);
        }
        order.setOrderItems(orderItems);
        order.setTotal(total);
        orderRepository.save(order);
        cart.getCartItems().clear();
        return orderMapper.toDto(order);
    }

    @Override
    public Page<OrderResponseDto> getHistoryOrders(Long userId, Pageable pageable) {
        return orderRepository
                .findAllByUserId(userId, pageable)
                .map(orderMapper::toDto);
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatusRequestDto status) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Can't find order by id: " + orderId));
        order.setStatus(status.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public Page<OrderItemResponseDto> findAllItemsByOrderId(Long orderId, Pageable pageable) {
        return orderItemRepository.findAllByOrderId(orderId, pageable)
                .map(orderItemMapper::toDto);
    }

    @Override
    public OrderItemResponseDto findItemByIdAndOrderId(Long orderId, Long itemId) {
        return orderItemRepository.findByIdAndOrderId(itemId, orderId)
                .map(orderItemMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find order item by id: " + itemId
                                + " for order id: " + orderId
                ));
    }
}
