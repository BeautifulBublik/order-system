package dev.beautifulbublik.kafka;

import dev.beautifulbublik.http.payment.PaymentMethod;
import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record OrderPaidEvent(
        Long orderId,
        Long paymentId,
        BigDecimal amount,
        PaymentMethod paymentMethod
) {
}
