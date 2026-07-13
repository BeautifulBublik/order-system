package dev.beautifulbublik.http.payment;

import jakarta.validation.constraints.Positive;import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record CreatePaymentRequestDto (
        @Positive
        Long orderId,
        PaymentMethod paymentMethod,
        @Positive
        BigDecimal amount

) {}
