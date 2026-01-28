package com.SoftwareOrdersUberEats.orderService.repository;

import com.SoftwareOrdersUberEats.orderService.entity.ProcessedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity, UUID> {
}
