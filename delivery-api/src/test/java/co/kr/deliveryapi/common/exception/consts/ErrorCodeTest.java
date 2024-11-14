package co.kr.deliveryapi.common.exception.consts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ErrorCodeTest {

    private StatusCode statusCode;
    private DetailCode detailCode;

    @BeforeEach
    public void setup() {
        statusCode = StatusCode.BAD_REQUEST;
        detailCode = DetailCode.INVALID_REQUEST_SYNTAX;
    }

    @DisplayName("정상적인 인자를 받을 경우 코드 생성을 확인하고 예상한 코드와 일치 한지 확인한다")
    @Test
    void test1() {
        // when
        ErrorCode errorCode = ErrorCode.of(statusCode, detailCode);

        // then
        assertNotNull(errorCode);
        assertEquals("4000", errorCode.getCode());
        assertEquals("INVALID_REQUEST_SYNTAX", errorCode.getDetail());
    }

    @DisplayName("null 인자로 생성할 수 없음을 확인한다")
    @Test
    void test2() {
        // when
        StatusCode nullStatusCode = null;
        DetailCode nullDetailCode = null;

        assertThrows(NullPointerException.class, () -> ErrorCode.of(nullStatusCode, nullDetailCode));
    }

}