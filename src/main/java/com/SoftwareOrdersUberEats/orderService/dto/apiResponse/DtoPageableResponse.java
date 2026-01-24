package com.SoftwareOrdersUberEats.orderService.dto.apiResponse;

import java.util.List;

public record DtoPageableResponse<T>(
        Long totalElements,
        Integer totalPages,
        List<T> data
) {
}
