package org.delivery.storeapi.domain.store.service;

import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.storeapi.domain.store.controller.model.StoreResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoreConverter {

    public StoreResponse toResponse(StoreEntity storeEntity) {
        return Optional.ofNullable(storeEntity)
                .map(it -> {
                    return StoreResponse.builder()
                            .id(it.getId())
                            .name(it.getName())
                            .address(it.getAddress())
                            .status(it.getStatus())
                            .category(it.getCategory())
                            .star(it.getStar())
                            .thumbnailUrl(it.getThumbnailUrl())
                            .minimumAmount(it.getMinimumAmount())
                            .phoneNumber(it.getPhoneNumber())
                            .build();
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "storeEntity is Null"));
    }

}
