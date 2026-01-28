package com.SoftwareOrdersUberEats.orderService.repository;

import com.SoftwareOrdersUberEats.orderService.entity.ProductsOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductsOrderRepository extends JpaRepository<ProductsOrderEntity, UUID> {
}
