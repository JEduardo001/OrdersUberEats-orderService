package com.SoftwareOrdersUberEats.orderService;

import com.SoftwareOrdersUberEats.orderService.entity.ProductsOrderEntity;
import com.SoftwareOrdersUberEats.orderService.repository.ProductsOrderRepository;
import com.SoftwareOrdersUberEats.orderService.service.ProductsOrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class ProductsOrderServiceTest {

    @Mock
    private ProductsOrderRepository productsOrderRepository;

    @InjectMocks
    private ProductsOrderService productsOrderService;

    @Test
    @DisplayName("Should save all product entities in the list")
    void saveProductsOrder_Success() {
        // Arrange
        List<ProductsOrderEntity> products = List.of(
                new ProductsOrderEntity(),
                new ProductsOrderEntity()
        );

        // Act
        productsOrderService.saveProductsOrder(products);

        // Assert
        verify(productsOrderRepository).saveAll(products);
    }
}