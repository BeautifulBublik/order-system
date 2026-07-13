package dev.beautifulbublik.orderservice.service.mappers;

import dev.beautifulbublik.orderservice.domain.OrderEntity;
import dev.beautifulbublik.http.order.CreateOrderRequestDto;
import dev.beautifulbublik.http.order.OrderShowDto;
import org.mapstruct.*;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface OrderEntityMapper {
    OrderEntity toEntity(CreateOrderRequestDto dto);
    OrderShowDto toOrderDto(OrderEntity orderEntity);
    @AfterMapping
    default void linkOrderItemEntities(@MappingTarget OrderEntity orderEntity) {
        orderEntity
                .getItems()
                .forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
    }
}
