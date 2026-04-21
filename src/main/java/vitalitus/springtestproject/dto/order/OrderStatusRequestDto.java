package vitalitus.springtestproject.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import vitalitus.springtestproject.model.Order;

@Getter
@Setter
public class OrderStatusRequestDto {
    @NotNull(message = "Status cannot be null")
    private Order.Status status;
}
