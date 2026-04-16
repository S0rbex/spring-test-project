package vitalitus.springtestproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vitalitus.springtestproject.dto.order.OrderItemResponseDto;
import vitalitus.springtestproject.dto.order.OrderRequestDto;
import vitalitus.springtestproject.dto.order.OrderResponseDto;
import vitalitus.springtestproject.dto.order.OrderStatusRequestDto;
import vitalitus.springtestproject.model.User;
import vitalitus.springtestproject.service.OrderService;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@Tag(name = "Order management", description = "Endpoints for managing orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Place an order",
            description = "Create a new order from shopping cart items")
    public OrderResponseDto placeOrder(
            Authentication authentication,
            @RequestBody @Valid OrderRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.placeOrder(user, requestDto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get order history", description = "Retrieve current user's order history")
    public Page<OrderResponseDto> getOrderHistory(
            Authentication authentication,
            Pageable pageable
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.getHistoryOrders(user.getId(), pageable);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status", description = "Allow admin to change order status")
    public OrderResponseDto updateStatus(
            @PathVariable Long id,
            @RequestBody @Valid OrderStatusRequestDto requestDto
    ) {
        return orderService.updateOrderStatus(id, requestDto);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get all items for specific order",
            description = "Retrieve all items within an order")
    public Page<OrderItemResponseDto> getAllOrderItems(
            @PathVariable Long orderId,
            Pageable pageable
    ) {
        return orderService.findAllItemsByOrderId(orderId, pageable);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get specific order item",
            description = "Retrieve a specific item by ID within an order")
    public OrderItemResponseDto getOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId
    ) {
        return orderService.findItemByIdAndOrderId(orderId, itemId);
    }
}
