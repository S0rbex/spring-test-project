package vitalitus.springtestproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vitalitus.springtestproject.dto.order.OrderItemResponseDto;
import vitalitus.springtestproject.dto.order.OrderRequestDto;
import vitalitus.springtestproject.dto.order.OrderResponseDto;
import vitalitus.springtestproject.dto.order.OrderStatusRequestDto;
import vitalitus.springtestproject.model.User;

public interface OrderService {
    OrderResponseDto placeOrder(User user, OrderRequestDto orderRequestDto);

    Page<OrderResponseDto> getHistoryOrders(Long userId, Pageable pageable);

    OrderResponseDto updateOrderStatus(Long orderId, OrderStatusRequestDto status);

    Page<OrderItemResponseDto> findAllItemsByOrderId(Long orderId, Pageable pageable);

    OrderItemResponseDto findItemByIdAndOrderId(Long orderId, Long itemId);
}
