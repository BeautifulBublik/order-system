package dev.beautifulbublik.orderservice.service.mappers;

import dev.beautifulbublik.http.order.CreateOrderRequestDto;
import dev.beautifulbublik.http.order.OrderItemRequestDto;
import dev.beautifulbublik.http.order.OrderItemShowDto;
import dev.beautifulbublik.http.order.OrderShowDto;
import dev.beautifulbublik.http.order.OrderStatus;
import dev.beautifulbublik.orderservice.domain.OrderEntity;
import dev.beautifulbublik.orderservice.domain.OrderItemEntity;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-10T15:43:50+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.3.0.jar, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class OrderEntityMapperImpl implements OrderEntityMapper {

    @Override
    public OrderEntity toEntity(CreateOrderRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setCustomerId( dto.customerId() );
        orderEntity.setAddress( dto.address() );
        orderEntity.setItems( orderItemRequestDtoSetToOrderItemEntitySet( dto.items() ) );

        linkOrderItemEntities( orderEntity );

        return orderEntity;
    }

    @Override
    public OrderShowDto toOrderDto(OrderEntity orderEntity) {
        if ( orderEntity == null ) {
            return null;
        }

        Long id = null;
        Long customerId = null;
        String address = null;
        BigDecimal totalAmount = null;
        String courierName = null;
        Integer etaMinutes = null;
        OrderStatus orderStatus = null;
        Set<OrderItemShowDto> items = null;

        id = orderEntity.getId();
        customerId = orderEntity.getCustomerId();
        address = orderEntity.getAddress();
        totalAmount = orderEntity.getTotalAmount();
        courierName = orderEntity.getCourierName();
        etaMinutes = orderEntity.getEtaMinutes();
        orderStatus = orderEntity.getOrderStatus();
        items = orderItemEntitySetToOrderItemShowDtoSet( orderEntity.getItems() );

        OrderShowDto orderShowDto = new OrderShowDto( id, customerId, address, totalAmount, courierName, etaMinutes, orderStatus, items );

        return orderShowDto;
    }

    protected OrderItemEntity orderItemRequestDtoToOrderItemEntity(OrderItemRequestDto orderItemRequestDto) {
        if ( orderItemRequestDto == null ) {
            return null;
        }

        OrderItemEntity orderItemEntity = new OrderItemEntity();

        orderItemEntity.setItemId( orderItemRequestDto.itemId() );
        orderItemEntity.setQuantity( orderItemRequestDto.quantity() );
        orderItemEntity.setName( orderItemRequestDto.name() );

        return orderItemEntity;
    }

    protected Set<OrderItemEntity> orderItemRequestDtoSetToOrderItemEntitySet(Set<OrderItemRequestDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<OrderItemEntity> set1 = LinkedHashSet.newLinkedHashSet( set.size() );
        for ( OrderItemRequestDto orderItemRequestDto : set ) {
            set1.add( orderItemRequestDtoToOrderItemEntity( orderItemRequestDto ) );
        }

        return set1;
    }

    protected OrderItemShowDto orderItemEntityToOrderItemShowDto(OrderItemEntity orderItemEntity) {
        if ( orderItemEntity == null ) {
            return null;
        }

        Long id = null;
        Long itemId = null;
        Integer quantity = null;
        BigDecimal priceAtPurchase = null;

        id = orderItemEntity.getId();
        itemId = orderItemEntity.getItemId();
        quantity = orderItemEntity.getQuantity();
        priceAtPurchase = orderItemEntity.getPriceAtPurchase();

        OrderItemShowDto orderItemShowDto = new OrderItemShowDto( id, itemId, quantity, priceAtPurchase );

        return orderItemShowDto;
    }

    protected Set<OrderItemShowDto> orderItemEntitySetToOrderItemShowDtoSet(Set<OrderItemEntity> set) {
        if ( set == null ) {
            return null;
        }

        Set<OrderItemShowDto> set1 = LinkedHashSet.newLinkedHashSet( set.size() );
        for ( OrderItemEntity orderItemEntity : set ) {
            set1.add( orderItemEntityToOrderItemShowDto( orderItemEntity ) );
        }

        return set1;
    }
}
