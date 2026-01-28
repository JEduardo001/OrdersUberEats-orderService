package com.SoftwareOrdersUberEats.orderService.interfaces;

public interface IConsumer {
    void handleCreateOrder(String rawEvent);
    void handleStockReserved(String rawEvent);
    void handleStockReservedFailed(String rawEvent);
}
