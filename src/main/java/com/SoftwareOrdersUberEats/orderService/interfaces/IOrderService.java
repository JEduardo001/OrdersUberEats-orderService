package com.SoftwareOrdersUberEats.orderService.interfaces;

import com.SoftwareOrdersUberEats.orderService.dto.order.DtoCreateOrder;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoCreateOrderConfirm;
import com.SoftwareOrdersUberEats.orderService.dto.order.DtoResultCreateOrder;
import com.SoftwareOrdersUberEats.orderService.enums.statusCreateResource.ResultChangeStatusOrderEnum;
import com.SoftwareOrdersUberEats.orderService.enums.statusResource.StatusResourceOrderEnum;

public interface IOrderService {

    DtoResultCreateOrder create(DtoCreateOrder request);
    ResultChangeStatusOrderEnum changeStatusOrder(DtoCreateOrderConfirm request, StatusResourceOrderEnum status);

}
