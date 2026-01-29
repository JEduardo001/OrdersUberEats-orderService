package com.SoftwareOrdersUberEats.orderService.controller;

import com.SoftwareOrdersUberEats.orderService.constant.ApiBase;
import com.SoftwareOrdersUberEats.orderService.dto.apiResponse.DtoResponseApi;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoUpdateOrder;
import com.SoftwareOrdersUberEats.orderService.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiBase.apiBase + "order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @GetMapping
    public ResponseEntity<DtoResponseApi> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "15") int size){
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponseApi.builder()
                .status(HttpStatus.OK.value())
                .message("Orders obtained")
                .data(orderService.getAll(page,size))
                .build()
        );
    }

    @GetMapping("/{idOrder}")
    public ResponseEntity<DtoResponseApi> getOrder(@PathVariable UUID idOrder){
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponseApi.builder()
                .status(HttpStatus.OK.value())
                .message("Order obtained")
                .data(orderService.get(idOrder))
                .build()
        );
    }

    @PutMapping()
    public ResponseEntity<DtoResponseApi> updateOrder(@Valid @RequestBody DtoUpdateOrder  request){
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponseApi.builder()
                .status(HttpStatus.OK.value())
                .message("Order updated")
                .data(orderService.update(request))
                .build()
        );
    }

}
