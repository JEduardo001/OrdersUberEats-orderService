package com.SoftwareOrdersUberEats.orderService.exception.exceptionHandler;

import com.SoftwareOrdersUberEats.orderService.dto.apiResponse.DtoResponseApiWithoutData;
import com.SoftwareOrdersUberEats.orderService.exception.order.OrderNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(1)
public class OrderExceptionHandler {

    private ResponseEntity<DtoResponseApiWithoutData> buildResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(DtoResponseApiWithoutData.builder()
                        .status(status.value())
                        .message(message)
                        .build());
    }

    // Validaciones de parametros
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<DtoResponseApiWithoutData> orderNotFoundException(OrderNotFoundException ex){
        return buildResponse(HttpStatus.NOT_FOUND, "Order not found");
    }
}
