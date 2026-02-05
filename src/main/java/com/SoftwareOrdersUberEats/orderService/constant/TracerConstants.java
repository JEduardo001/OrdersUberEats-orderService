package com.SoftwareOrdersUberEats.orderService.constant;

public class TracerConstants {
    public static final String CORRELATION_KEY = "correlationId";
    public static final String CORRELATION_HEADER = "X-Correlation-Id";

    //MESSAGE
    public static final String MESSAGE_SAVE_EVENT = "Save event topic {}";
    public static final String MESSAGE_SEND_EVENT = "Send event id event{}";
    public static final String MESSAGE_UPDATE_STATUS_ORDER = "Update status order id {}";
    public static final String MESSAGE_LIST_PRODUCTS_ORDER_SAVED = "List products order saved ";
    public static final String MESSAGE_SAVE_ORDER = "Save order id order {}";
    public static final String MESSAGE_UPDATE_ORDER = "Update order id {}";
    public static final String MESSAGE_DATA_VALIDATION_CREATE_ORDER_ERROR = "Data create order not valid ";
    public static final String MESSAGE_ODER_NOT_FOUND = "Order not found";
    //EXCEPTION
    public static final String EXCEPTION_ALREADY_EVENT_PROCESSED = "Exception already event processed {}";
    //ERROR
    public static final String ERROR_SEND_EVENT = "Error send event";
}
