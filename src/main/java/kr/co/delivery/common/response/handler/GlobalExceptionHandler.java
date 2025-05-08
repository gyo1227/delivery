package kr.co.delivery.common.response.handler;

import kr.co.delivery.common.exception.CustomException;
import kr.co.delivery.common.exception.enums.ReasonCode;
import kr.co.delivery.common.exception.enums.StatusCode;
import kr.co.delivery.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 예외
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiResponse<?>> handleCustomException(CustomException e) {
        final MediaType contentType = new MediaType(
                MediaType.APPLICATION_JSON,
                StandardCharsets.UTF_8
        );

        final int status = e.getError().errorCode().statusCode().getCode();
        final String code = e.getError().errorCode().getErrorCode();
        final String message = e.getError().errorMessage();

        ApiResponse<?> response = ApiResponse.error(code, message);

        return ResponseEntity
                .status(status)
                .contentType(contentType)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("handleMethodArgumentNotValidException: {}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        String code = String.valueOf(
                StatusCode.BAD_REQUEST.getCode() * 10 + ReasonCode.REQUIRED_PARAMETER_VALIDATION_ERROR.getCode());

        ApiResponse<?> response = ApiResponse.error(code, "입력값이 올바르지 않습니다. 각 항목을 확인해주세요.", fieldErrors);

        return ResponseEntity
                .status(StatusCode.BAD_REQUEST.getCode())
                .body(response);
    }

    // -------------------------------------------------------------------------------------------------

    // 예상하지 못한 예외
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.warn("{} : handle exception : {}", e.getClass().getSimpleName(), e.getMessage());
        e.printStackTrace();

        String code = String.valueOf(StatusCode.INTERNAL_SERVER_ERROR.getCode() * 10 + ReasonCode.UNEXPECTED_ERROR.getCode());

        return ResponseEntity
                .status(StatusCode.INTERNAL_SERVER_ERROR.getCode())
                .body(ApiResponse.error(code, "예상하지 못한 서버 오류가 발생했습니다."));
    }

}
