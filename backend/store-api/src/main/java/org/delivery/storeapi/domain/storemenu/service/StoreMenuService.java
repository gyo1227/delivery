package org.delivery.storeapi.domain.storemenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.error.StoreUserErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.storemenu.StoreMenuRepository;
import org.delivery.db.storemenu.enums.StoreMenuStatus;
import org.delivery.storeapi.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.storeapi.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.storeapi.domain.storeuser.model.StoreUserSession;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class StoreMenuService {

    private final StoreRepository storeRepository;

    private final StoreMenuRepository storeMenuRepository;
    private final StoreMenuConverter storeMenuConverter;

    public StoreMenuResponse register(
            StoreMenuRegisterRequest request,
            StoreUserSession storeUserSession
    ) {
        var storeEntity = storeRepository.findFirstByIdAndOwnerId(request.getStoreId(), storeUserSession.getId())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));

        var entity = Optional.ofNullable(request)
                .map(it -> {
                    return StoreMenuEntity.builder()
                            .storeId(storeEntity.getId())
                            .name(it.getName())
                            .amount(it.getAmount())
                            .status(StoreMenuStatus.SOLD_OUT)
                            .thumbnailUrl(it.getThumbnailUrl())
                            .likeCount(0)
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "StoreMenuRegisterRequest is Null"));

        var savedEntity = storeMenuRepository.save(entity);
        var response = storeMenuConverter.toResponse(savedEntity);
        return response;
    }
}
