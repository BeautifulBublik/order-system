package dev.beautifulbublik.http.payment;

import lombok.Builder;import java.math.BigDecimal;

public record CreatePaymentResponseDto(
        Long paymentId,
        Long orderId,
        PaymentMethod paymentMethod,
        PaymentStatus status,
        BigDecimal amount
) {}
