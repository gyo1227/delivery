package org.delivery.storeapi.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.delivery.common.api.ApiResponse;
import org.delivery.common.error.ErrorCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE)   // 가장 마지막에 실행
public class StoreGlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<Object>> exception(Exception exception) {
        log.error("", exception);

        return ResponseEntity
                .status(500)
                .body(ApiResponse.Error(ErrorCode.SERVER_ERROR));
    }
}
