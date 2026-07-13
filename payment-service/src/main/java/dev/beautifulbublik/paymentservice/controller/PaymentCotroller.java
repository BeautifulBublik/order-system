package dev.beautifulbublik.paymentservice.controller;

import dev.beautifulbublik.http.payment.CreatePaymentRequestDto;
import dev.beautifulbublik.http.payment.CreatePaymentResponseDto;
import dev.beautifulbublik.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@Slf4j
@RequiredArgsConstructor
public class PaymentCotroller {
    private final PaymentService paymentService;
    @PostMapping()
    public ResponseEntity<CreatePaymentResponseDto> post(@RequestBody CreatePaymentRequestDto createPaymentRequestDto) {
        log.info("Received request: paymentRequest={}", createPaymentRequestDto);
        var payment=paymentService.makePayment(createPaymentRequestDto);
        return ResponseEntity.ok(payment);
    }
}
