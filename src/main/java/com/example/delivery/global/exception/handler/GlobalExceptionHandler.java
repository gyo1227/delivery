package com.example.delivery.global.exception.handler;

import com.example.delivery.global.exception.ApiError;
import com.example.delivery.global.exception.ApiException;
import com.example.delivery.global.exception.ErrorCode;
import com.example.delivery.global.exception.ReasonCode;
import com.example.delivery.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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


    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        int status = HttpStatus.UNPROCESSABLE_ENTITY.value();
        String code = String.valueOf(status * 10 + ReasonCode.REQUIRED_PARAMETER_VALIDATION_ERROR.getCode());

        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        for(FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(code, "입력값이 올바르지 않습니다. 각 항목을 확인해주세요.", errors));
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResponse<?>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("{}: {}", e.getClass().getSimpleName(), e.getMessage());

        int status = HttpStatus.METHOD_NOT_ALLOWED.value();
        String code = String.valueOf(status * 10 + ReasonCode.REQUEST_METHOD_NOT_SUPPORTED.getCode());

        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(code, "지원하지 않는 HTTP 메서드 입니다."));
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
