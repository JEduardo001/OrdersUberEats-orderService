package com.SoftwareOrdersUberEats.orderService.dto.order;

import com.SoftwareOrdersUberEats.orderService.entity.ProductsOrderEntity;

import jakarta.validation.constraints.NotNull;
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
public class DtoCreateOrder {
    @NotNull
    private UUID idUser;
    @NotNull
    private List<DtoProductsOrder> products;
}
