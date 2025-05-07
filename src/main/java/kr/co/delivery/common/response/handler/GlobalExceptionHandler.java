package kr.co.delivery.common.response.handler;

import kr.co.delivery.common.exception.CustomException;
import kr.co.delivery.common.exception.enums.ReasonCode;
import kr.co.delivery.common.exception.enums.StatusCode;
import kr.co.delivery.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.charset.StandardCharsets;

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
