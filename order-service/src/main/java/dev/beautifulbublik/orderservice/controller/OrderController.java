package dev.beautifulbublik.orderservice.controller;

import dev.beautifulbublik.http.order.CreateOrderRequestDto;
import dev.beautifulbublik.http.order.OrderShowDto;
import dev.beautifulbublik.orderservice.service.OrderProcessor;
import dev.beautifulbublik.orderservice.service.mappers.OrderEntityMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderProcessor orderProcessor;
    private final OrderEntityMapper orderEntityMapper;
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderShowDto> getOrderById(@PathVariable Long id) {
        log.info("Retrieving order with id {}", id);
        var value=orderProcessor.getOrderByIdOrThrow(id);
        return ResponseEntity.ok(orderEntityMapper.toOrderDto(value));
    }

    @PostMapping
    public ResponseEntity<OrderShowDto> post(@Valid @RequestBody CreateOrderRequestDto createOrderRequestDto) {
        log.info("Creating order: request={}", createOrderRequestDto);
        var saved = orderProcessor.create(createOrderRequestDto);
        return ResponseEntity.ok(orderEntityMapper.toOrderDto(saved));
        
    }
    @PostMapping("/{id}/pay")
    public ResponseEntity<OrderShowDto> payOrder(@Valid @RequestBody OrderPaymentRequest request, @PathVariable Long id) {
        log.info("Paying order with id={}, request={}", id, request);
        var entity = orderProcessor.processPayment(request, id);
        return ResponseEntity.ok(orderEntityMapper.toOrderDto(entity));

    }
}
