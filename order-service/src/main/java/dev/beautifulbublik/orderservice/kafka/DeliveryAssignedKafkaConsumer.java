package dev.beautifulbublik.orderservice.kafka;

import dev.beautifulbublik.kafka.DeliveryAssignedEvent;
import dev.beautifulbublik.orderservice.service.OrderProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@EnableKafka
@Configuration
@Slf4j
@RequiredArgsConstructor
public class DeliveryAssignedKafkaConsumer {
    private final OrderProcessor orderProcessor;

    @KafkaListener(
            topics = "${delivery-assigned-topic}",
            containerFactory ="deliveryAssignedEventEventListenerFactory",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(DeliveryAssignedEvent deliveryAssignedEvent){
        orderProcessor.processDeliveryAssigned(deliveryAssignedEvent);
        log.info("Received delivery assigned event: {}", deliveryAssignedEvent);

    }
}
