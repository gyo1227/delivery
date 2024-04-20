package org.delivery.common.exception;

import lombok.Getter;
import org.delivery.common.error.ErrorCodeIfs;

@Getter
public class ApiException extends RuntimeException {

    private final ErrorCodeIfs errorCodeIfs;
    private final String errorMessage;

    public ApiException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getMessage());
        this.errorCodeIfs = errorCodeIfs;
        this.errorMessage = errorCodeIfs.getMessage();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, String errorMessage) {
        super(errorMessage);
        this.errorCodeIfs = errorCodeIfs;
        this.errorMessage = errorMessage;
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable t) {
        super(t);
        this.errorCodeIfs = errorCodeIfs;
        this.errorMessage = errorCodeIfs.getMessage();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable t, String errorMessage) {
        super(t);
        this.errorCodeIfs = errorCodeIfs;
        this.errorMessage = errorMessage;
    }
}
