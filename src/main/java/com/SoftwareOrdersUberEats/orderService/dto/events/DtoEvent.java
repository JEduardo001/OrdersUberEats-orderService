package com.SoftwareOrdersUberEats.orderService.dto.events;

import com.SoftwareOrdersUberEats.orderService.enums.typeEvents.TypeEventEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DtoEvent<T> {
    private TypeEventEnum typeEvent;
    private UUID idEvent;
    private String correlationId;
    private T data;
}
