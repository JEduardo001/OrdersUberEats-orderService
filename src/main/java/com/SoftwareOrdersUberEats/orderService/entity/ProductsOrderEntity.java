package com.SoftwareOrdersUberEats.orderService.entity;


import com.SoftwareOrdersUberEats.orderService.enums.statesResource.StatesResourceOrderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.Order;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "products_order_table")
@Entity
public class ProductsOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne()
    @JoinColumn(name = "id_order")
    private OrderEntity order;
    private UUID idProduct;
    private Integer quantityProducts;
    private Instant createAt;
    private Instant deleteAt;

}
