package com.SoftwareOrdersUberEats.orderService.mapper;

import com.SoftwareOrdersUberEats.orderService.dto.order.DtoCreateOrder;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoCreateOrderConfirm;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoOrder;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoUpdateOrder;
import com.SoftwareOrdersUberEats.orderService.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.core.annotation.Order;

@Mapper(componentModel = "spring", uses = {ProductsOrderMapper.class})
public interface OrderMapper {

    DtoOrder toDto(OrderEntity request);
    DtoCreateOrderConfirm toDtoCreateOrderConfirm(DtoCreateOrder request);
    @Mapping(target = "id", ignore  = true)
    void updateOrder(DtoUpdateOrder request,@MappingTarget OrderEntity actualOrder);
}
