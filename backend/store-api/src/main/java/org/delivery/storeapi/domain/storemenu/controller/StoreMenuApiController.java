package org.delivery.storeapi.domain.storemenu.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.common.api.ApiResponse;
import org.delivery.storeapi.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.storeapi.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.storeapi.domain.storemenu.service.StoreMenuService;
import org.delivery.storeapi.domain.storeuser.model.StoreUserSession;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store-menu")
public class StoreMenuApiController {

    private final StoreMenuService storeMenuService;

    @PostMapping("/register")
    public ApiResponse<StoreMenuResponse> register(
            @Valid @RequestBody StoreMenuRegisterRequest request,
            Authentication authentication
    ) {
        StoreUserSession storeUserSession = (StoreUserSession) authentication.getPrincipal();
        var response = storeMenuService.register(request, storeUserSession);
        return ApiResponse.Ok(response);
    }
}
