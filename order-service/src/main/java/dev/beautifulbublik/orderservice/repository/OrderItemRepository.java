package dev.beautifulbublik.orderservice.repository;

import dev.beautifulbublik.orderservice.domain.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}
