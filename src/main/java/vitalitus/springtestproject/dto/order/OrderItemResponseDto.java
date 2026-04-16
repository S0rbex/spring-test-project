package vitalitus.springtestproject.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto {
    private Long id;
    private Long bookId;
    private Integer quantity;
}
