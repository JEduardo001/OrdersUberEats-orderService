package com.SoftwareOrdersUberEats.orderService.dto.order;

import com.SoftwareOrdersUberEats.orderService.dto.productsOrder.DtoProductsOrder;
import com.SoftwareOrdersUberEats.orderService.enums.statusCreateResource.ResultCreateOrdenEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DtoCreateOrderConfirm {
    private UUID idOrder;
    private UUID idUser;
    private ResultCreateOrdenEnum resultEvent;
    private List<DtoProductsOrder> products;
}
