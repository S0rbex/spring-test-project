package vitalitus.springtestproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vitalitus.springtestproject.config.MapperConfig;
import vitalitus.springtestproject.dto.order.OrderResponseDto;
import vitalitus.springtestproject.model.Order;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    OrderResponseDto toDto(Order order);
}
