package dev.beautifulbublik.deliveryservice.kafka;

import dev.beautifulbublik.deliveryservice.service.DeliveryService;
import dev.beautifulbublik.kafka.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@EnableKafka
@Configuration
@Slf4j
@RequiredArgsConstructor
public class OrderPaidKafkaConsumer {
    private final DeliveryService deliveryService;

    @KafkaListener(
            topics = "${order-paid-topic}",
            containerFactory ="orderPaidEventEventListenerFactory",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(OrderPaidEvent orderPaidEvent){
        deliveryService.processOrderPaid(orderPaidEvent);
        log.info("Received order paid event: {}", orderPaidEvent);

    }
}
