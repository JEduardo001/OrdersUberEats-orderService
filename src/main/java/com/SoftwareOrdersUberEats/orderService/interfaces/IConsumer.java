package com.SoftwareOrdersUberEats.orderService.interfaces;

public interface IConsumer {
    void handleCreateOrder(String rawEvent,String correlationId);
    void handleStockReserved(String rawEvent,String correlationId);
    void handleStockReservedFailed(String rawEvent,String correlationId);
}
