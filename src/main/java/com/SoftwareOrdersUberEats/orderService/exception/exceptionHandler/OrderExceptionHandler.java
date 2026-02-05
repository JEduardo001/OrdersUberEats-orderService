package com.SoftwareOrdersUberEats.orderService.exception.exceptionHandler;

import com.SoftwareOrdersUberEats.orderService.dto.apiResponse.DtoResponseApiWithoutData;
import com.SoftwareOrdersUberEats.orderService.exception.order.CannotUpdateOrCanceledTerminatedOrderException;
import com.SoftwareOrdersUberEats.orderService.exception.order.OrderNotFoundException;
import com.SoftwareOrdersUberEats.orderService.service.MappedDiagnosticService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Order(1)
@Slf4j
@AllArgsConstructor
public class OrderExceptionHandler {

    private final MappedDiagnosticService mappedDiagnosticService;

    private ResponseEntity<DtoResponseApiWithoutData> buildResponse(HttpStatus status, String message, Exception ex) {
        log.warn("Business exception: {} - Message: {}", ex.getClass().getSimpleName(), message);

        return ResponseEntity.status(status)
                .body(DtoResponseApiWithoutData.builder()
                        .status(status.value())
                        .message(message)
                        .correlationId(mappedDiagnosticService.getIdCorrelation())
                        .build());
    }

    // Validaciones de parametros
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<DtoResponseApiWithoutData> orderNotFoundException(OrderNotFoundException ex){
        return buildResponse(HttpStatus.NOT_FOUND, "Order not found",ex);
    }

    @ExceptionHandler(CannotUpdateOrCanceledTerminatedOrderException.class)
    public ResponseEntity<DtoResponseApiWithoutData> cannotUpdateOrCanceledTerminatedOrderException(CannotUpdateOrCanceledTerminatedOrderException ex){
        return buildResponse(HttpStatus.BAD_REQUEST, "Cannot update canceled or terminated orders",ex);
    }
}
