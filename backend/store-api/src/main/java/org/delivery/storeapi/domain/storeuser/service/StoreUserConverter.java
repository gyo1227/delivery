package org.delivery.storeapi.domain.storeuser.service;

import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.storeapi.domain.storeuser.controller.model.StoreUserResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoreUserConverter {

    public StoreUserResponse toResponse(StoreUserEntity storeUserEntity) {
        return Optional.ofNullable(storeUserEntity)
                .map(it -> {
                    return StoreUserResponse.builder()
                            .id(it.getId())
                            .name(it.getName())
                            .email(it.getEmail())
                            .status(it.getStatus())
                            .role(it.getRole())
                            .registeredAt(it.getRegisteredAt())
                            .unregisteredAt(it.getUnregisteredAt())
                            .lastLoginAt(it.getLastLoginAt())
                            .build();
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "StoreUserEntity is Null"));
    }
}
