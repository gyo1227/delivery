package com.example.delivery.global.exception;

public interface ApiError {
    ErrorCode errorCode();
    String message();
}