package com.SoftwareOrdersUberEats.orderService.dto.order;

import com.SoftwareOrdersUberEats.orderService.enums.statusCreateResource.ResultCreateOrdenEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DtoResultCreateOrder {
    private UUID idOrder;
    private ResultCreateOrdenEnum status;
}
