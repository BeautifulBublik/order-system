package dev.beautifulbublik.orderservice.kafka;


import dev.beautifulbublik.kafka.DeliveryAssignedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import dev.beautifulbublik.kafka.OrderPaidEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;


import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
    @Bean
    DefaultKafkaProducerFactory<Long, OrderPaidEvent> orderPaidEventProducerFactory() {
        Map<String, Object> producerProperties = new HashMap<>();
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092" );
        return new DefaultKafkaProducerFactory<>(producerProperties);
    }
    @Bean
    KafkaTemplate<Long, OrderPaidEvent> orderPaidEventKafkaTemplate(
            DefaultKafkaProducerFactory<Long, OrderPaidEvent> orderPaidEventProducerFactory){
        return new KafkaTemplate<>(orderPaidEventProducerFactory);
    }
    @Bean
    ConsumerFactory<Long, DeliveryAssignedEvent> deliveryAssignedEventConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        props.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "dev.beautifulbublik.kafka");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    KafkaListenerContainerFactory<?> deliveryAssignedEventEventListenerFactory(
            ConsumerFactory<Long, DeliveryAssignedEvent> deliveryAssignedEventConsumerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Long, DeliveryAssignedEvent>();
        factory.setConsumerFactory(deliveryAssignedEventConsumerFactory);
        factory.setBatchListener(false);
        return factory;
    }
}
