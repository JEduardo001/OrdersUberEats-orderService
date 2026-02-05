package com.SoftwareOrdersUberEats.orderService.service;

import com.SoftwareOrdersUberEats.orderService.interfaces.IMappedDiagnostic;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import static com.SoftwareOrdersUberEats.orderService.constant.TracerConstants.CORRELATION_KEY;

@NoArgsConstructor
@Service
public class MappedDiagnosticService implements IMappedDiagnostic {

    public String getIdCorrelation(){
        return MDC.get(CORRELATION_KEY);
    }
}
