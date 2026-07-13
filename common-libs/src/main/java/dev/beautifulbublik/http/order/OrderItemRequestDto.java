package dev.beautifulbublik.http.order;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder(toBuilder = true)
public record OrderItemRequestDto(
        @Positive
        Long itemId,
        @Positive
        @Min(value = 1)
        @Max(value = 100)
        Integer quantity,
        @NotBlank(message = " Enter the correct product")
        String name
) {}

