package com.SoftwareOrdersUberEats.orderService.service;

import com.SoftwareOrdersUberEats.orderService.dto.order.DtoCreateOrder;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoCreateOrderConfirm;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoResultCreateOrder;
import com.SoftwareOrdersUberEats.orderService.entity.OrderEntity;
import com.SoftwareOrdersUberEats.orderService.entity.ProductsOrderEntity;
import com.SoftwareOrdersUberEats.orderService.enums.statusCreateResource.ResultChangeStatusOrderEnum;
import com.SoftwareOrdersUberEats.orderService.enums.statusCreateResource.ResultCreateOrdenEnum;
import com.SoftwareOrdersUberEats.orderService.enums.statusResource.StatusResourceOrderEnum;
import com.SoftwareOrdersUberEats.orderService.interfaces.IOrderService;
import com.SoftwareOrdersUberEats.orderService.mapper.ProductsOrderMapper;
import com.SoftwareOrdersUberEats.orderService.repository.OrderRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final Validator validator;
    private final ProductsOrderMapper productsOrderMapper;
    private final ProductsOrderService productsOrderService;

    public List<OrderEntity> getAll(){
      return orderRepository.findAll();
    }

    @Override
    @Transactional
    public DtoResultCreateOrder create(DtoCreateOrder request) {

        Set<ConstraintViolation<DtoCreateOrder>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            return DtoResultCreateOrder.builder()
                    .idOrder(null)
                    .status(ResultCreateOrdenEnum.VALIDATION_ERROR)
                    .build();
        }

        OrderEntity order = orderRepository.save(
                OrderEntity.builder()
                        .createAt(Instant.now())
                        .status(StatusResourceOrderEnum.PENDING_TO_CONFIRM)
                        .idUser(request.getIdUser())
                        .build()
        );

        List<ProductsOrderEntity> products =
                productsOrderMapper.toEntity(request.getProducts());

        for (ProductsOrderEntity product : products) {
            product.setOrder(order);
        }

        productsOrderService.saveProductsOrder(products);

        return DtoResultCreateOrder.builder()
                .idOrder(order.getId())
                .status(ResultCreateOrdenEnum.CREATED)
                .build();
    }


    @Override
    @Transactional
    public ResultChangeStatusOrderEnum changeStatusOrder(DtoCreateOrderConfirm request, StatusResourceOrderEnum status){
        Optional<OrderEntity> order = orderRepository.findById(request.getIdOrder());

        if(order.isEmpty()){
            return ResultChangeStatusOrderEnum.ORDER_NOT_FOUND;
        }

        order.get().setStatus(status);
        orderRepository.save(order.get());
        return ResultChangeStatusOrderEnum.UPDATED;
    }
}
