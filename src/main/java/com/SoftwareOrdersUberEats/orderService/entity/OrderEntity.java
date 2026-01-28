package com.SoftwareOrdersUberEats.orderService.entity;

import com.SoftwareOrdersUberEats.orderService.enums.statusResource.StatusResourceOrderEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "order_table")
@Entity
@ToString(exclude = "products")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID idUser;
    private StatusResourceOrderEnum status;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<ProductsOrderEntity> products;
    private Instant createAt;
    private Instant deleteAt;

}
