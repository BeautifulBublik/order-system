package dev.beautifulbublik.orderservice.external;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import dev.beautifulbublik.http.payment.CreatePaymentResponseDto;
import dev.beautifulbublik.http.payment.CreatePaymentRequestDto;

@HttpExchange(
        accept = "application/json",
        contentType = "application/json",
        url = "/api/v1/payment"
)
public interface PaymentHttpClient {
    @PostExchange
    CreatePaymentResponseDto createPayment(@RequestBody CreatePaymentRequestDto dto);
}
