package com.SoftwareOrdersUberEats.orderService.controller;

import com.SoftwareOrdersUberEats.orderService.constant.ApiBase;
import com.SoftwareOrdersUberEats.orderService.dto.apiResponse.DtoResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(ApiBase.apiBase + "order")
public class OrderController {

    @GetMapping()
    public String test(){
        return "Hola";
    }
}
