package kr.co.delivery.common.exception.consts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorCodeTest {

    @Test
    @DisplayName("유효한 StatusCode와 ReasonCode를 기반으로 ErrorCode 객체를 생성할 수 있다")
    void test01() {
        // given
        StatusCode statusCode = StatusCode.INTERNAL_SERVER_ERROR;
        ReasonCode reasonCode = ReasonCode.UNEXPECTED_ERROR;

        // when
        ErrorCode errorCode = ErrorCode.of(statusCode, reasonCode);

        // then
        assertNotNull(errorCode);
        assertEquals("5000", errorCode.getCode());
    }

    @Test
    @DisplayName("StatusCode가 null이면 IllegalArgumentException이 발생해야 한다")
    void test02() {
        // given
        ReasonCode reasonCode = ReasonCode.UNEXPECTED_ERROR;


        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ErrorCode.of(null, reasonCode));

        // then
        assertEquals("StatusCode and ReasonCode cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("ReasonCode가 null이면 IllegalArgumentException이 발생해야 한다")
    void test03() {
        // given
        StatusCode statusCode = StatusCode.INTERNAL_SERVER_ERROR;

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ErrorCode.of(statusCode, null));

        // then
        assertEquals("StatusCode and ReasonCode cannot be null.", exception.getMessage());
    }

}