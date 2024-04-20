package org.delivery.userapi.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.delivery.common.api.ApiResponse;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)   // 최우선으로 처리
public class UserApiExceptionHandler {

    private static final String METHOD_ARGUMENT_NOT_VALID_ERROR_MESSAGE_FORMAT = "%s 필드는 %s (입력한 값: %s)";

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ApiResponse<Object>> apiException(
            ApiException apiException
    ) {
        log.error("", apiException);

        var errorCode = apiException.getErrorCodeIfs();

        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ApiResponse.Error(errorCode, apiException.getErrorMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        log.error("", e);

        FieldError fieldError = e.getFieldErrors().get(0);
        String errorMessage = String.format(METHOD_ARGUMENT_NOT_VALID_ERROR_MESSAGE_FORMAT, fieldError.getField(),
                fieldError.getDefaultMessage(), fieldError.getRejectedValue());

        return ResponseEntity.badRequest()
                .body(ApiResponse.Error(ErrorCode.BAD_REQUEST, errorMessage));
    }
}
