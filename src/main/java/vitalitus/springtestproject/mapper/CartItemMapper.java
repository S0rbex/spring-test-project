package vitalitus.springtestproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vitalitus.springtestproject.config.MapperConfig;
import vitalitus.springtestproject.dto.cart.CartItemDto;
import vitalitus.springtestproject.model.CartItem;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemDto toDto(CartItem cartItem);
}
