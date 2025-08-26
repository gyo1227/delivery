package com.example.delivery.domain.user.exception;

import com.example.delivery.domain.user.exception.enums.UserErrorCode;
import com.example.delivery.global.exception.ApiException;
import lombok.Getter;

@Getter
public class UserException extends ApiException {

    private final UserErrorCode userErrorCode;

    public UserException(UserErrorCode userErrorCode) {
        super(userErrorCode);
        this.userErrorCode = userErrorCode;
    }
}
