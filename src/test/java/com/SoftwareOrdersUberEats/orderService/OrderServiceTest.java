package com.SoftwareOrdersUberEats.orderService;

import com.SoftwareOrdersUberEats.orderService.dto.apiResponse.DtoPageableResponse;
import com.SoftwareOrdersUberEats.orderService.dto.order.*;
import com.SoftwareOrdersUberEats.orderService.entity.OrderEntity;
import com.SoftwareOrdersUberEats.orderService.entity.ProductsOrderEntity;
import com.SoftwareOrdersUberEats.orderService.enums.statusCreateResource.ResultChangeStatusOrderEnum;
import com.SoftwareOrdersUberEats.orderService.enums.statusCreateResource.ResultCreateOrdenEnum;
import com.SoftwareOrdersUberEats.orderService.enums.statusResource.StatusResourceOrderEnum;
import com.SoftwareOrdersUberEats.orderService.exception.order.CannotUpdateOrCanceledTerminatedOrderException;
import com.SoftwareOrdersUberEats.orderService.mapper.OrderMapper;
import com.SoftwareOrdersUberEats.orderService.mapper.ProductsOrderMapper;
import com.SoftwareOrdersUberEats.orderService.repository.OrderRepository;
import com.SoftwareOrdersUberEats.orderService.service.OrderService;
import com.SoftwareOrdersUberEats.orderService.service.ProductsOrderService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private Validator validator;
    @Mock
    private ProductsOrderMapper productsOrderMapper;
    @Mock
    private ProductsOrderService productsOrderService;
    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("Should return pageable orders")
    void getAll_Success() {
        OrderEntity entity = new OrderEntity();
        Page<OrderEntity> page = new PageImpl<>(List.of(entity));
        when(orderRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(orderMapper.toDto(any())).thenReturn(DtoOrder.builder().build());

        DtoPageableResponse result = orderService.getAll(0, 10);

        assertNotNull(result);
        verify(orderRepository).findAll(any(PageRequest.class));
    }

    @Test
    @DisplayName("Should update order status when order is active")
    void update_Success() {
        UUID orderId = UUID.randomUUID();
        DtoUpdateOrder request = DtoUpdateOrder.builder()
                .id(orderId)
                .status(StatusResourceOrderEnum.PENDING_TO_DO)
                .build();

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderId);
        orderEntity.setStatus(StatusResourceOrderEnum.PENDING_TO_CONFIRM);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(orderRepository.save(any())).thenReturn(orderEntity);

        orderService.update(request);

        verify(orderRepository).save(orderEntity);
    }

    @Test
    @DisplayName("Should throw exception when trying to update a FINISHED order")
    void update_ThrowsException_WhenFinished() {
        UUID orderId = UUID.randomUUID();
        DtoUpdateOrder request = DtoUpdateOrder.builder().id(orderId).build();
        OrderEntity finishedOrder = new OrderEntity();
        finishedOrder.setStatus(StatusResourceOrderEnum.FINISHED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(finishedOrder));

        assertThrows(CannotUpdateOrCanceledTerminatedOrderException.class, () ->
                orderService.update(request)
        );
    }

    @Test
    @DisplayName("Should create order and save products")
    void create_Success() {
        UUID userId = UUID.randomUUID();
        DtoCreateOrder request = DtoCreateOrder.builder()
                .idUser(userId)
                .products(new ArrayList<>())
                .build();

        OrderEntity savedOrder = OrderEntity.builder().id(UUID.randomUUID()).build();

        when(validator.validate(request)).thenReturn(Collections.emptySet());
        when(orderRepository.save(any())).thenReturn(savedOrder);
        when(productsOrderMapper.toEntity(any())).thenReturn(List.of(new ProductsOrderEntity()));

        DtoResultCreateOrder result = orderService.create(request);

        assertEquals(ResultCreateOrdenEnum.CREATED, result.getStatus());
        assertNotNull(result.getIdOrder());
        verify(productsOrderService).saveProductsOrder(anyList());
    }

    @Test
    @DisplayName("Should return VALIDATION_ERROR when create request is invalid")
    void create_ValidationError() {
        DtoCreateOrder request = DtoCreateOrder.builder().build();
        ConstraintViolation<DtoCreateOrder> violation = mock(ConstraintViolation.class);

        when(validator.validate(request)).thenReturn(Set.of(violation));

        DtoResultCreateOrder result = orderService.create(request);

        assertEquals(ResultCreateOrdenEnum.VALIDATION_ERROR, result.getStatus());
        verify(orderRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should change status successfully")
    void changeStatusOrder_Success() {
        UUID orderId = UUID.randomUUID();
        DtoCreateOrderConfirm request = DtoCreateOrderConfirm.builder().idOrder(orderId).build();
        OrderEntity order = new OrderEntity();
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        ResultChangeStatusOrderEnum result = orderService.changeStatusOrder(request, StatusResourceOrderEnum.PENDING_TO_DO);

        assertEquals(ResultChangeStatusOrderEnum.UPDATED, result);
        verify(orderRepository).save(order);
    }

    @Test
    @DisplayName("Should return ORDER_NOT_FOUND when changing status of non-existent order")
    void changeStatusOrder_NotFound() {
        DtoCreateOrderConfirm request = DtoCreateOrderConfirm.builder().idOrder(UUID.randomUUID()).build();
        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        ResultChangeStatusOrderEnum result = orderService.changeStatusOrder(request, StatusResourceOrderEnum.PENDING_TO_DO);

        assertEquals(ResultChangeStatusOrderEnum.ORDER_NOT_FOUND, result);
    }
}