package com.SoftwareOrdersUberEats.orderService.mapper;

import com.SoftwareOrdersUberEats.orderService.dto.order.DtoCreateOrder;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoCreateOrderConfirm;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoOrder;
import com.SoftwareOrdersUberEats.orderService.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.springframework.core.annotation.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderEntity toDto(DtoOrder request);
    DtoCreateOrderConfirm toDtoCreateOrderConfirm(DtoCreateOrder request);
}
