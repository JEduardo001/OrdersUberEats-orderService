package com.SoftwareOrdersUberEats.orderService.service;

import com.SoftwareOrdersUberEats.orderService.dto.apiResponse.DtoPageableResponse;
import com.SoftwareOrdersUberEats.orderService.dto.order.*;
import com.SoftwareOrdersUberEats.orderService.entity.OrderEntity;
import com.SoftwareOrdersUberEats.orderService.entity.ProductsOrderEntity;
import com.SoftwareOrdersUberEats.orderService.enums.statusCreateResource.ResultChangeStatusOrderEnum;
import com.SoftwareOrdersUberEats.orderService.enums.statusCreateResource.ResultCreateOrdenEnum;
import com.SoftwareOrdersUberEats.orderService.enums.statusResource.StatusResourceOrderEnum;
import com.SoftwareOrdersUberEats.orderService.exception.order.CannotUpdateOrCanceledTerminatedOrderException;
import com.SoftwareOrdersUberEats.orderService.exception.order.OrderNotFoundException;
import com.SoftwareOrdersUberEats.orderService.interfaces.IOrderService;
import com.SoftwareOrdersUberEats.orderService.mapper.OrderMapper;
import com.SoftwareOrdersUberEats.orderService.mapper.ProductsOrderMapper;
import com.SoftwareOrdersUberEats.orderService.repository.OrderRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final Validator validator;
    private final ProductsOrderMapper productsOrderMapper;
    private final ProductsOrderService productsOrderService;
    private final OrderMapper orderMapper;


    @Override
    public DtoPageableResponse getAll(int page,int size){
        Page<OrderEntity> orders = orderRepository.findAll(PageRequest.of(page,size));
        List<DtoOrder> listOrders = orders.get().map(orderMapper::toDto).collect(Collectors.toList());
      return new DtoPageableResponse<>(
              orders.getTotalElements(),
              orders.getTotalPages(),
              listOrders
      );
    }

    @Override
    public DtoOrder get(UUID id){
        return orderMapper.toDto(orderRepository.findById(id).orElseThrow(OrderNotFoundException::new));
    }

    @Override
    public DtoOrder update(DtoUpdateOrder request){
        OrderEntity orderEntity = orderRepository.findById(request.getId()).orElseThrow(OrderNotFoundException::new);

        if(orderEntity.getStatus().equals(StatusResourceOrderEnum.CANCELED) ||
            orderEntity.getStatus().equals(StatusResourceOrderEnum.FINISHED)){
            throw new CannotUpdateOrCanceledTerminatedOrderException();
        }
        orderEntity.setStatus(request.getStatus());
        orderEntity.setDeleteAt(request.getStatus().equals(StatusResourceOrderEnum.DELETED) ? Instant.now() : null);
        return orderMapper.toDto(orderRepository.save(orderEntity));
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
