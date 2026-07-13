package dev.beautifulbublik.http.order;

import java.math.BigDecimal;

public record OrderItemShowDto(
        Long id,
        Long itemId,
        Integer quantity,
        BigDecimal priceAtPurchase
) {}
