package org.delivery.storeapi.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.delivery.common.api.ApiResponse;
import org.delivery.common.exception.ApiException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)   // 최우선으로 처리
public class StoreApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ApiResponse<Object>> apiException(
            ApiException apiException
    ) {
        log.error("", apiException);

        var errorCode = apiException.getErrorCodeIfs();

        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ApiResponse.Error(errorCode, apiException.getErrorMessage()));
    }
}
