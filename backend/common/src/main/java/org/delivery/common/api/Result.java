package org.delivery.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.error.ErrorCodeIfs;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    private Integer code;
    private String message;

    public static Result Ok() {
        return Result.builder()
                .code(ErrorCode.OK.getErrorCode())
                .message(ErrorCode.OK.getMessage())
                .build();
    }

    public static Result Error(ErrorCodeIfs errorCodeIfs) {
        return Result.builder()
                .code(errorCodeIfs.getErrorCode())
                .message(errorCodeIfs.getMessage())
                .build();
    }

    public static Result Error(ErrorCodeIfs errorCodeIfs, Throwable t) {
        return Result.builder()
                .code(errorCodeIfs.getErrorCode())
                .message(t.getLocalizedMessage())
                .build();
    }

    public static Result Error(ErrorCodeIfs errorCodeIfs, String message) {
        return Result.builder()
                .code(errorCodeIfs.getErrorCode())
                .message(message)
                .build();
    }
}
