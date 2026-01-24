package com.SoftwareOrdersUberEats.orderService.dto.events;

import com.SoftwareOrdersUberEats.orderService.enums.statesCreateResource.ResultEventEnum;
import com.SoftwareOrdersUberEats.orderService.enums.typeEvents.TypeEventEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DtoEvent<T> {
    private TypeEventEnum typeEvent;
    private ResultEventEnum resultEvent;
    private T data;
}
