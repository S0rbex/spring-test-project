package vitalitus.springtestproject.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;
}
