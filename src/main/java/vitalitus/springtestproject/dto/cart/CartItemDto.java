package vitalitus.springtestproject.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemDto {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private int quantity;
}
