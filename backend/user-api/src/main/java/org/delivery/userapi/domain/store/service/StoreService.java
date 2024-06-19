package org.delivery.userapi.domain.store.service;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.userapi.domain.store.controller.model.StoreResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreConverter storeConverter;
    private final StoreRepository storeRepository;

    public List<StoreResponse> searchByCategory(StoreCategory storeCategory) {
        var list = storeRepository.findAllByCategoryOrderByStarDesc(storeCategory);

        return list.stream()
                .map(storeConverter::toResponse)
                .collect(Collectors.toList());
    }
}
