package org.delivery.userapi.domain.storemenu.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.common.api.ApiResponse;
import org.delivery.userapi.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.userapi.domain.storemenu.service.StoreMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/store-menu")
public class StoreMenuOpenApiController {

    private final StoreMenuService storeMenuService;

    @GetMapping("/search")
    public ApiResponse<List<StoreMenuResponse>> search(
            @RequestParam Long storeId
    ) {
        var response = storeMenuService.searchByStoreId(storeId);
        return ApiResponse.Ok(response);
    }
}
