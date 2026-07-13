package dev.beautifulbublik.http.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.util.Set;

@Builder(toBuilder = true)
public record CreateOrderRequestDto(
        @Positive
        Long customerId,
        @NotBlank(message = "Enter the correct address")
        String address,
        @Valid
        Set<OrderItemRequestDto> items
) {}
