package com.SoftwareOrdersUberEats.orderService.controller;

import com.SoftwareOrdersUberEats.orderService.constant.ApiBase;
import com.SoftwareOrdersUberEats.orderService.dto.apiResponse.DtoResponseApi;
import com.SoftwareOrdersUberEats.orderService.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(ApiBase.apiBase + "order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @GetMapping
    public ResponseEntity<DtoResponseApi> getAllOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponseApi.builder()
                .status(HttpStatus.OK.value())
                .message("Orders obtained")
                .data(orderService.getAll())
                .build()
        );
    }
}
