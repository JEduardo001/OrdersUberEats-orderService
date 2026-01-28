package com.SoftwareOrdersUberEats.orderService.service;


import com.SoftwareOrdersUberEats.orderService.entity.ProductsOrderEntity;
import com.SoftwareOrdersUberEats.orderService.repository.ProductsOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductsOrderService {

    private ProductsOrderRepository productsOrderRepository;

    public void saveProductsOrder(List<ProductsOrderEntity> request){
        productsOrderRepository.saveAll(request);
    }
}
