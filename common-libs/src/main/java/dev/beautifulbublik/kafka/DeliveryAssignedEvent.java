package dev.beautifulbublik.kafka;

import lombok.Builder;

@Builder(toBuilder = true)
public record DeliveryAssignedEvent(
        Long orderId,
        String courierName,
        Integer etaMinutes
) {
}
