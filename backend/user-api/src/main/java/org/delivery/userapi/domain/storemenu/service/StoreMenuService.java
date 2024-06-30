package org.delivery.userapi.domain.storemenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.storemenu.StoreMenuRepository;
import org.delivery.userapi.domain.storemenu.controller.model.StoreMenuResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StoreMenuService {

    private final StoreMenuConverter storeMenuConverter;
    private final StoreMenuRepository storeMenuRepository;

    public List<StoreMenuResponse> searchByStoreId(Long storeId) {
        var list = storeMenuRepository.findAllByStoreIdOrderByLikeCountDesc(storeId);
        return list.stream()
                .map(storeMenuConverter::toResponse)
                .collect(Collectors.toList());
    }
}
