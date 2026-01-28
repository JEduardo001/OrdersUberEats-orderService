package com.SoftwareOrdersUberEats.orderService.entity;

import com.SoftwareOrdersUberEats.orderService.enums.statesResource.StatesResourceOrderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "order_table")
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private StatesResourceOrderEnum status;
    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<ProductsOrderEntity> products;
    private Instant createAt;
    private Instant deleteAt;

}
