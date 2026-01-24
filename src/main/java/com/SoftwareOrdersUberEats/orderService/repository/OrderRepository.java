package com.SoftwareOrdersUberEats.orderService.repository;

import com.SoftwareOrdersUberEats.orderService.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
}
