package kr.co.delivery.api.common.response.handler;

import kr.co.delivery.api.common.response.ErrorResponse;
import kr.co.delivery.common.exception.GlobalErrorException;
import kr.co.delivery.common.exception.consts.ReasonCode;
import kr.co.delivery.common.exception.consts.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalErrorException.class)
    protected ResponseEntity<ErrorResponse> handleGlobalErrorException(GlobalErrorException e) {
        final MediaType contentType = new MediaType(
                MediaType.APPLICATION_JSON,
                StandardCharsets.UTF_8
        );
        final int status = e.getBaseErrorCode().errorCode().statusCode().getCode();
        ErrorResponse response = ErrorResponse.of(e.getBaseErrorCode().errorCode().getCode(), e.getBaseErrorCode().errorMessage());

        return ResponseEntity
                .status(status)
                .contentType(contentType)
                .body(response);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ErrorResponse handleException(Exception e) {
        log.warn("{} : handle exception : {}", e.getClass().getSimpleName(), e.getMessage());
        e.printStackTrace();

        String code = String.valueOf(StatusCode.INTERNAL_SERVER_ERROR.getCode() * 10 + ReasonCode.UNEXPECTED_ERROR.getCode());
        return ErrorResponse.of(code, StatusCode.INTERNAL_SERVER_ERROR.name());
    }
}
