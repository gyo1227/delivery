package com.example.delivery.global.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ApiError apiError;

    public ApiException(ApiError apiError) {
        this.apiError = apiError;
    }

    @Override
    public String getMessage() {
        return apiError.message();
    }
}
