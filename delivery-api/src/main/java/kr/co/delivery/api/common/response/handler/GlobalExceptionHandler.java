package kr.co.delivery.api.common.response.handler;

import kr.co.delivery.api.common.response.ErrorResponse;
import kr.co.delivery.common.exception.GlobalErrorException;
import kr.co.delivery.common.exception.consts.DetailCode;
import kr.co.delivery.common.exception.consts.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller 하위 계층에서 발생하는 전역 예외를 처리
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Delivery Custom Exception을 처리하는 메서드
     */
    @ExceptionHandler(GlobalErrorException.class)
    protected ResponseEntity<ErrorResponse> handleGlobalException(GlobalErrorException e) {
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

    /**
     * API 호출 시 객체 혹은 파라미터 데이터 값이 유효하지 않은 경우
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("handleMethodArgumentNotValidException: {}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        String code = String.valueOf(
                StatusCode.UNPROCESSABLE_CONTENT.getCode() * 10 + DetailCode.REQUIRED_PARAMETERS_MISSING_IN_REQUEST_BODY.getCode());

        return ErrorResponse.error(code, StatusCode.UNPROCESSABLE_CONTENT.name(), fieldErrors);
    }

    //--------------------------------------------------------------------

    /**
     * 예상하지 못한 예외가 발생한 경우
     * @param e Exception
     * @return ResponseEntity<ErrorResponse>
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ErrorResponse handleException(Exception e) {
        log.warn("{} : handle exception : {}", e.getClass().getSimpleName(), e.getMessage());
        e.printStackTrace();

        String code = String.valueOf(StatusCode.INTERNAL_SERVER_ERROR.getCode() * 10 + DetailCode.UNEXPECTED_ERROR.getCode());
        return ErrorResponse.of(code, StatusCode.INTERNAL_SERVER_ERROR.name());
    }
}

