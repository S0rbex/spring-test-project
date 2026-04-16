package vitalitus.springtestproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vitalitus.springtestproject.config.MapperConfig;
import vitalitus.springtestproject.dto.order.OrderItemResponseDto;
import vitalitus.springtestproject.model.OrderItem;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    OrderItemResponseDto toDto(OrderItem orderItem);
}
