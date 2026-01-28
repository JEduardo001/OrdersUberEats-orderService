package com.SoftwareOrdersUberEats.orderService.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "products_order_table")
@Entity
@ToString(exclude = "order")
public class ProductsOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "id_order")
    private OrderEntity order;
    private UUID idProduct;
    private Integer quantityProducts;
    private Instant createAt;
    private Instant deleteAt;

}
