package dev.beautifulbublik.orderservice.controller;

import dev.beautifulbublik.http.payment.PaymentMethod;

public record OrderPaymentRequest(
       PaymentMethod paymentMethod
) {}
