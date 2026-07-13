package dev.beautifulbublik.deliveryservice.repository;

import dev.beautifulbublik.deliveryservice.domain.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DeliveryEntityRepository extends JpaRepository< DeliveryEntity,Long> {
    Optional<DeliveryEntity> findByOrderId(Long orderId);
}
