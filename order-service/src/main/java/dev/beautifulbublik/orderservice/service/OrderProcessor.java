package dev.beautifulbublik.orderservice.service;

import dev.beautifulbublik.http.payment.CreatePaymentResponseDto;
import dev.beautifulbublik.http.payment.PaymentStatus;
import dev.beautifulbublik.kafka.DeliveryAssignedEvent;
import dev.beautifulbublik.kafka.OrderPaidEvent;
import dev.beautifulbublik.orderservice.controller.OrderPaymentRequest;
import dev.beautifulbublik.orderservice.domain.OrderEntity;
import dev.beautifulbublik.orderservice.domain.OrderItemEntity;
import dev.beautifulbublik.http.order.OrderStatus;
import dev.beautifulbublik.http.order.CreateOrderRequestDto;
import dev.beautifulbublik.orderservice.external.PaymentHttpClient;
import dev.beautifulbublik.orderservice.repository.OrderRepository;
import dev.beautifulbublik.http.payment.CreatePaymentRequestDto;
import dev.beautifulbublik.orderservice.service.mappers.OrderEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProcessor {
    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final PaymentHttpClient paymentHttpClient;
    private final KafkaTemplate<Long, OrderPaidEvent> kafkaTemplate;
    @Value("${order-paid-topic}")
    private String kafkaTopic;

    public OrderEntity getOrderByIdOrThrow(Long id){
        return orderRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Entity with id `%s` not found".formatted(id)));
    }

    public OrderEntity create(CreateOrderRequestDto createOrderRequestDto) {
        var entity=orderEntityMapper.toEntity(createOrderRequestDto);
        calculatePricingForOrder(entity);
        entity.setOrderStatus(OrderStatus.PENDING_PAYMENT);
        return orderRepository.save(entity);
    }

    private void calculatePricingForOrder(OrderEntity entity) {
        BigDecimal totalPrice=BigDecimal.ZERO;
        for(OrderItemEntity orderItem:entity.getItems()){
            var randomPrice= ThreadLocalRandom.current().nextDouble(10, 500);
            orderItem.setPriceAtPurchase(BigDecimal.valueOf(randomPrice));
            totalPrice= orderItem.getPriceAtPurchase()
                    .multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                    .add(totalPrice);


        }
        entity.setTotalAmount(totalPrice);

    }
    public OrderEntity processPayment(OrderPaymentRequest request, Long id){
        var entity =getOrderByIdOrThrow(id);
        if (!entity.getOrderStatus().equals(OrderStatus.PENDING_PAYMENT)) {
            throw new RuntimeException("Order must be in status PENDING_PAYMENT");
        }
        var response = paymentHttpClient.createPayment(CreatePaymentRequestDto.builder()
                        .orderId(id)
                        .paymentMethod(request.paymentMethod())
                        .amount(entity.getTotalAmount())
                .build());
        var status = response.status().equals(PaymentStatus.PAYMENT_SUCCEEDED)?
                OrderStatus.PAID : OrderStatus.PAYMENT_FAILED;

        entity.setOrderStatus(status);
        sendOrderPaidEvent(entity, response);
        return orderRepository.save(entity);
    }

    private void sendOrderPaidEvent(OrderEntity entity, CreatePaymentResponseDto responseDto) {
        kafkaTemplate.send(
                kafkaTopic,
                entity.getId(),
                OrderPaidEvent.builder()
                        .orderId(entity.getId())
                        .paymentMethod(responseDto.paymentMethod())
                        .amount(responseDto.amount())
                        .paymentId(responseDto.paymentId())
                        .build()
        ).thenAccept((result)->
                log.info("Order Paid event sent: id={}", entity.getId()));
    }

    public void processDeliveryAssigned(DeliveryAssignedEvent event) {
        var order = getOrderByIdOrThrow(event.orderId());
        if (!order.getOrderStatus().equals(OrderStatus.PAID)) {
            processIncorrectDeliveryState(order);
            return;
        }

        order.setOrderStatus(OrderStatus.DELIVERY_ASSIGNED);
        order.setCourierName(event.courierName());
        order.setEtaMinutes(event.etaMinutes());
        orderRepository.save(order);
        log.info("Order delivery assigned processed: orderId={}", order.getId());
    }

    private void processIncorrectDeliveryState(OrderEntity order) {
        if (order.getOrderStatus().equals(OrderStatus.DELIVERY_ASSIGNED)) {
            log.info("Order delivery already processed: orderId={}", order.getId());
        } else {
            log.error("Trying to assign delivery but order have incorrect state: state={}", order.getId());
        }
    }
}
