package org.delivery.storeapi.domain.store.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.common.api.ApiResponse;
import org.delivery.storeapi.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.storeapi.domain.store.controller.model.StoreResponse;
import org.delivery.storeapi.domain.store.service.StoreService;
import org.delivery.storeapi.domain.storeuser.model.StoreUserSession;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store")
public class StoreApiController {

    private final StoreService storeService;

    @PostMapping("/register")
    public ApiResponse<StoreResponse> register(
            @Valid @RequestBody StoreRegisterRequest request,
            Authentication authentication
    ) {
        StoreUserSession storeUserSession = (StoreUserSession) authentication.getPrincipal();
        var response = storeService.register(request, storeUserSession);
        return ApiResponse.Ok(response);
    }
}
