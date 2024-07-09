package org.delivery.storeapi.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.storeapi.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.storeapi.domain.store.controller.model.StoreResponse;
import org.delivery.storeapi.domain.store.controller.model.StoreStatusUpdateRequest;
import org.delivery.storeapi.domain.storeuser.model.StoreUserSession;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreConverter storeConverter;
    private final StoreRepository storeRepository;

    public StoreResponse register(
            StoreRegisterRequest request,
            StoreUserSession storeUserSession
    ) {

        var entity = Optional.ofNullable(request)
                .map(it -> {
                    return StoreEntity.builder()
                            .ownerId(storeUserSession.getId())
                            .name(it.getName())
                            .address(it.getAddress())
                            .status(StoreStatus.CLOSE)
                            .category(it.getStoreCategory())
                            .star(0)
                            .thumbnailUrl(it.getThumbnailUrl())
                            .minimumAmount(it.getMinimumAmount())
                            .phoneNumber(it.getPhoneNumber())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "StoreRegisterRequest is Null"));

        var savedEntity = storeRepository.save(entity);
        var response = storeConverter.toResponse(savedEntity);

        return response;
    }

    public StoreResponse updateStatus(StoreStatusUpdateRequest request, StoreUserSession storeUserSession) {
        var storeEntity = storeRepository.findFirstByIdAndOwnerId(request.getStoreId(), storeUserSession.getId())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));

        storeEntity.setStatus(request.getStatus());

        var savedEntity = storeRepository.save(storeEntity);
        var response = storeConverter.toResponse(savedEntity);

        return response;
    }
}
