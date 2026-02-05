package com.SoftwareOrdersUberEats.orderService.entity;

import com.SoftwareOrdersUberEats.orderService.enums.statusResource.StatusResourceOrderEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID idUser;
    @Enumerated(EnumType.STRING)
    @Schema(
            example = "PENDING_TO_DO",
            allowableValues = {
                    "PENDING_TO_DO",
                    "PENDING_TO_CONFIRM",
                    "CANCELED",
                    "DISABLE",
                    "FINISHED",
                    "DELETED"
            }
    )
    private StatusResourceOrderEnum status;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", orphanRemoval = true,
            cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    private List<ProductsOrderEntity> products;
    private Instant createAt;
    private Instant deleteAt;

}
