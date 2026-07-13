package dev.beautifulbublik.deliveryservice.kafka;



import dev.beautifulbublik.kafka.DeliveryAssignedEvent;
import dev.beautifulbublik.kafka.OrderPaidEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
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
import org.springframework.kafka.support.serializer.JsonDeserializer;


import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Bean
    DefaultKafkaProducerFactory<Long, DeliveryAssignedEvent> deliveryAssignedEventDefaultKafkaProducerFactory() {
        Map<String, Object> producerProperties = new HashMap<>();
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new DefaultKafkaProducerFactory<>(producerProperties);
    }
    @Bean
    KafkaTemplate<Long, DeliveryAssignedEvent> deliveryAssignedEventKafkaTemplate(
            DefaultKafkaProducerFactory<Long, DeliveryAssignedEvent> defaultKafkaProducerFactory){
        return new KafkaTemplate<>(defaultKafkaProducerFactory);
    }
    @Bean
    ConsumerFactory<Long, OrderPaidEvent> orderPaidEventConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        props.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "dev.beautifulbublik.kafka");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    KafkaListenerContainerFactory<?> orderPaidEventEventListenerFactory(
            ConsumerFactory<Long, OrderPaidEvent> orderPaidEventConsumerFactory
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<Long, OrderPaidEvent>();
        factory.setConsumerFactory(orderPaidEventConsumerFactory);
        factory.setBatchListener(false);
        return factory;
    }
}
