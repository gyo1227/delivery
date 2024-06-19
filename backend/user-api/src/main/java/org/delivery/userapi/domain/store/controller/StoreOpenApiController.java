package org.delivery.userapi.domain.store.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.common.api.ApiResponse;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.userapi.domain.store.controller.model.StoreResponse;
import org.delivery.userapi.domain.store.service.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/store")
public class StoreOpenApiController {

    private final StoreService storeService;

    @GetMapping("/search")
    public ApiResponse<List<StoreResponse>> search(
            @Valid @RequestParam(required = false) StoreCategory storeCategory
    ) {
        var response = storeService.searchByCategory(storeCategory);
        return ApiResponse.Ok(response);
    }
}
