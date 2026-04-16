package vitalitus.springtestproject.dto.order;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private String status;
    private LocalDateTime orderDate;
    private Set<OrderItemResponseDto> orderItems;

}
