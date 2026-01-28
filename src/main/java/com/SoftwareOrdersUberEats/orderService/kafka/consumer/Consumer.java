package com.SoftwareOrdersUberEats.orderService.kafka.consumer;

import com.SoftwareOrdersUberEats.orderService.dto.events.DtoEvent;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoCreateOrder;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoCreateOrderConfirm;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoResultCreateOrder;
import com.SoftwareOrdersUberEats.orderService.enums.statusCreateResource.ResultChangeStatusOrderEnum;
import com.SoftwareOrdersUberEats.orderService.enums.statusResource.StatusResourceOrderEnum;
import com.SoftwareOrdersUberEats.orderService.enums.typeEvents.TypeEventEnum;
import com.SoftwareOrdersUberEats.orderService.interfaces.IConsumer;
import com.SoftwareOrdersUberEats.orderService.mapper.OrderMapper;
import com.SoftwareOrdersUberEats.orderService.service.OrderService;
import com.SoftwareOrdersUberEats.orderService.service.OutboxEventService;
import com.SoftwareOrdersUberEats.orderService.service.ProcessedEventService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Service
@AllArgsConstructor
public class Consumer implements IConsumer {

    private final OrderService orderService;
    private final OutboxEventService outboxEventService;
    private final OrderMapper orderMapper;
    private final ProcessedEventService processedEventService;

    private String parseRawEvent(String rawEvent){
        String json = rawEvent;
        if (rawEvent.startsWith("\"") && rawEvent.endsWith("\"")) {
            json = new ObjectMapper().readValue(rawEvent, String.class);
        }
        return json;
    }

    private boolean isEventProcessed(UUID id){
        return processedEventService.isEventProcessed(id);
    }

    private void saveEventProcessed(UUID id){
        processedEventService.save(id);
    }

    @KafkaListener(topics = "order.requested", groupId = "orders")
    @Transactional
    @Override
    public void handleCreateOrder(String rawEvent) {

        String json = parseRawEvent(rawEvent);

        DtoEvent<DtoCreateOrder> dto = new ObjectMapper().readValue(
                json,
                new TypeReference<DtoEvent<DtoCreateOrder>>() {}
        );

        if(isEventProcessed(dto.getIdEvent())){
            System.out.println("ya existe: ");
            return;
        }

        DtoResultCreateOrder result = orderService.create(dto.getData());
        DtoCreateOrderConfirm dtoCreateOrderConfirm = orderMapper.toDtoCreateOrderConfirm(dto.getData());
        dtoCreateOrderConfirm.setIdOrder(result.getIdOrder());
        dtoCreateOrderConfirm.setResultEvent(result.getStatus());

        DtoEvent<DtoCreateOrderConfirm> event = DtoEvent.<DtoCreateOrderConfirm>builder()
                .data(dtoCreateOrderConfirm)
                .idEvent(UUID.randomUUID())
                .typeEvent(TypeEventEnum.CREATE)
                .build();
        outboxEventService.saveEvent(event, "order.created.pending");
        saveEventProcessed(dto.getIdEvent());
    }

    @KafkaListener(topics = "inventory.stock.reserved", groupId = "orders")
    @Transactional
    @Override
    public void handleStockReserved(String rawEvent) {
        String json = parseRawEvent(rawEvent);

        DtoEvent<DtoCreateOrderConfirm> dto = new ObjectMapper().readValue(
                json,
                new TypeReference<DtoEvent<DtoCreateOrderConfirm>>() {}
        );

        if(isEventProcessed(dto.getIdEvent())){
            return;
        }

        ResultChangeStatusOrderEnum result = orderService.changeStatusOrder(dto.getData(),StatusResourceOrderEnum.PENDING_TO_DO);

        if(result.equals(ResultChangeStatusOrderEnum.ORDER_NOT_FOUND)){
            DtoEvent<DtoCreateOrderConfirm> event = DtoEvent.<DtoCreateOrderConfirm>builder()
                    .data(dto.getData())
                    .idEvent(UUID.randomUUID())
                    .typeEvent(TypeEventEnum.UPDATE)
                    .build();

            outboxEventService.saveEvent(event, "changed.status.order.failed");
        }

        saveEventProcessed(dto.getIdEvent());
    }

    @KafkaListener(topics = "inventory.stock.reserved.failed", groupId = "orders")
    @Transactional
    @Override
    public void handleStockReservedFailed(String rawEvent) {
        String json = rawEvent;
        if (rawEvent.startsWith("\"") && rawEvent.endsWith("\"")) {
            json = new ObjectMapper().readValue(rawEvent, String.class);
        }

        DtoEvent<DtoCreateOrderConfirm> dto = new ObjectMapper().readValue(
                json,
                new TypeReference<DtoEvent<DtoCreateOrderConfirm>>() {}
        );

        if(isEventProcessed(dto.getIdEvent())){
            return;
        }

        orderService.changeStatusOrder(dto.getData(), StatusResourceOrderEnum.DISABLE);
        saveEventProcessed(dto.getIdEvent());
    }
}