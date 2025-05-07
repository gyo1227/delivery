package kr.co.delivery.common.exception;

import kr.co.delivery.common.exception.enums.ReasonCode;
import kr.co.delivery.common.exception.enums.StatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorCodeTest {

    @DisplayName("ErrorCode 생성 및 검증")
    @Test
    void test1() {
        // given
        StatusCode statusCode = StatusCode.INTERNAL_SERVER_ERROR;
        ReasonCode reasonCode = ReasonCode.UNEXPECTED_ERROR;

        // when
        ErrorCode errorCode = ErrorCode.of(statusCode, reasonCode);

        // then
        assertEquals(statusCode, errorCode.statusCode());
        assertEquals(reasonCode, errorCode.reasonCode());
    }

    @DisplayName("getErrorCode 메서드 확인")
    @Test
    void test2() {
        // given
        StatusCode statusCode = StatusCode.INTERNAL_SERVER_ERROR;
        ReasonCode reasonCode = ReasonCode.UNEXPECTED_ERROR;

        // when
        ErrorCode errorCode = ErrorCode.of(statusCode, reasonCode);
        String result = errorCode.getErrorCode();

        // then
        assertEquals("5000", result);
    }

}