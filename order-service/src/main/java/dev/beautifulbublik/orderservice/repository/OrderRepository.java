package dev.beautifulbublik.orderservice.repository;

import dev.beautifulbublik.orderservice.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
