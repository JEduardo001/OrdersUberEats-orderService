package com.SoftwareOrdersUberEats.orderService.interfaces;

import com.SoftwareOrdersUberEats.orderService.dto.apiResponse.DtoPageableResponse;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoCreateOrder;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoCreateOrderConfirm;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoOrder;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoResultCreateOrder;
import com.SoftwareOrdersUberEats.orderService.enums.statusCreateResource.ResultChangeStatusOrderEnum;
import com.SoftwareOrdersUberEats.orderService.enums.statusResource.StatusResourceOrderEnum;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoUpdateOrder;

import java.util.UUID;


public interface IOrderService {

    DtoResultCreateOrder create(DtoCreateOrder request);
    ResultChangeStatusOrderEnum changeStatusOrder(DtoCreateOrderConfirm request, StatusResourceOrderEnum status);
    DtoOrder update(DtoUpdateOrder request);
    DtoOrder get(UUID id);
    DtoPageableResponse getAll(int page, int size);
}
