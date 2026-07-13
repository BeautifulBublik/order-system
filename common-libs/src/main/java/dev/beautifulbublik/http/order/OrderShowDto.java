package dev.beautifulbublik.http.order;

import java.math.BigDecimal;
import java.util.Set;

public record OrderShowDto(
        Long id,
        Long customerId,
        String address,
        BigDecimal totalAmount,
        String courierName,
        Integer etaMinutes,
        OrderStatus orderStatus,
        Set<OrderItemShowDto> items
) {
}
