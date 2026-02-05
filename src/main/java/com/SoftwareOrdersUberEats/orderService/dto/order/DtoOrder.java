package com.SoftwareOrdersUberEats.orderService.dto.order;

import com.SoftwareOrdersUberEats.orderService.dto.productsOrder.DtoProductsOrder;
import com.SoftwareOrdersUberEats.orderService.enums.statusResource.StatusResourceOrderEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DtoOrder {

    private UUID id;
    private UUID idUser;
    private StatusResourceOrderEnum status;
    private List<DtoProductsOrder> products;
    private Instant createAt;
    private Instant deleteAt;
}
