package com.SoftwareOrdersUberEats.orderService.dto.order;

import com.SoftwareOrdersUberEats.orderService.enums.statusResource.StatusResourceOrderEnum;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DtoUpdateOrder {
    @NotNull
    private UUID id;
    @NotNull
    private StatusResourceOrderEnum status;
}
