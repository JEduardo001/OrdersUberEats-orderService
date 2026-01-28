package com.SoftwareOrdersUberEats.orderService.mapper;

import com.SoftwareOrdersUberEats.orderService.dto.order.DtoProductsOrder;
import com.SoftwareOrdersUberEats.orderService.entity.ProductsOrderEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface ProductsOrderMapper {

    List<ProductsOrderEntity> toEntity(List<DtoProductsOrder> request);
}
