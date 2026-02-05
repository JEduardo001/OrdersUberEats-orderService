package com.SoftwareOrdersUberEats.orderService.repository;

import com.SoftwareOrdersUberEats.orderService.entity.OutboxEventEntity;
import com.SoftwareOrdersUberEats.orderService.enums.statusEvent.StatusEventEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID> {
    List<OutboxEventEntity> findAllByStatusEvent(StatusEventEnum status);
}
