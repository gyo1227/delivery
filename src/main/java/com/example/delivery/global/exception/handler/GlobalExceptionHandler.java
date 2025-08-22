package com.example.delivery.global.exception.handler;

import com.example.delivery.global.exception.ApiException;
import com.example.delivery.global.exception.ErrorCode;
import com.example.delivery.global.exception.ApiError;
import com.example.delivery.global.exception.ReasonCode;
import com.example.delivery.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ApiResponse<?>> handleApiException(ApiException e) {
        log.warn("{}: {}", e.getClass().getSimpleName(), e.getMessage());

        final ApiError apiError = e.getApiError();
        final ErrorCode errorCode = apiError.errorCode();

        return ResponseEntity
                .status(errorCode.httpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiResponse.error(errorCode.getErrorCode(), apiError.message()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("{} handle exception : {} ", e.getClass().getSimpleName(), e.getMessage());

        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String code = String.valueOf(status * 10 + ReasonCode.UNEXPECTED_ERROR.getCode());

        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(code, "예상하지 못한 서버 오류가 발생했습니다."));
    }
}
