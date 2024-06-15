package org.delivery.storeapi.domain.storeuser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.error.StoreUserErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.db.storeuser.StoreUserRepository;
import org.delivery.db.storeuser.enums.StoreUserRole;
import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.delivery.storeapi.domain.storeuser.controller.model.StoreUserRegisterRequest;
import org.delivery.storeapi.domain.storeuser.controller.model.StoreUserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreUserService {

    private final PasswordEncoder passwordEncoder;

    private final StoreUserConverter storeUserConverter;
    private final StoreUserRepository storeUserRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$");

    public StoreUserResponse register(StoreUserRegisterRequest request) {
        var entity = Optional.ofNullable(request)
                .map(it -> {
                    Matcher matcher = EMAIL_PATTERN.matcher(it.getEmail());
                    if(!matcher.matches()) {
                        throw new ApiException(StoreUserErrorCode.VALIDATED_ERROR, "이메일 형식에 맞지않습니다.");
                    }

                    storeUserRepository.findByEmail(it.getEmail())
                            .ifPresent(storeUserEntity -> {
                                throw new ApiException(StoreUserErrorCode.DUPLICATED_ERROR, "중복된 이메일입니다.");
                            });

                    return StoreUserEntity.builder()
                            .email(it.getEmail())
                            .password(passwordEncoder.encode(it.getPassword()))
                            .name(it.getName())
                            .status(StoreUserStatus.REGISTERED)
                            .role(StoreUserRole.USER_ROLE)
                            .registeredAt(LocalDateTime.now())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "StoreUserRegisterRequest is Null"));

        var savedEntity = storeUserRepository.save(entity);
        var response = storeUserConverter.toResponse(savedEntity);

        return response;
    }

}
