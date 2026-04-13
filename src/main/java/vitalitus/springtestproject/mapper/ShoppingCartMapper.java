package vitalitus.springtestproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vitalitus.springtestproject.config.MapperConfig;
import vitalitus.springtestproject.dto.cart.ShoppingCartDto;
import vitalitus.springtestproject.model.ShoppingCart;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(source = "user.id", target = "userId")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    ShoppingCart toModel(ShoppingCartDto shoppingCartDto);
}
