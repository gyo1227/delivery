package org.delivery.storeapi.domain.storeuser.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.error.StoreUserErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.db.storeuser.StoreUserRepository;
import org.delivery.db.storeuser.enums.StoreUserRole;
import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.delivery.storeapi.domain.storeuser.controller.model.StoreUserLoginRequest;
import org.delivery.storeapi.domain.storeuser.controller.model.StoreUserRegisterRequest;
import org.delivery.storeapi.domain.storeuser.controller.model.StoreUserResponse;
import org.delivery.storeapi.domain.storeuser.model.StoreUserSession;
import org.delivery.storeapi.domain.token.model.TokenResponse;
import org.delivery.storeapi.domain.token.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class StoreUserService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final StoreUserConverter storeUserConverter;
    private final StoreUserRepository storeUserRepository;

    private final TokenService tokenService;

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

    public TokenResponse login(StoreUserLoginRequest request) {
        storeUserRepository.findFirstByEmailOrderByIdDesc(request.getEmail())
                .orElseThrow(() -> new ApiException(StoreUserErrorCode.USER_NOT_FOUND));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var response = tokenService.issueToken(authentication);
        return response;
    }

    public Optional<StoreUserEntity> getRegisterUser(String email) {
        return storeUserRepository.findFirstByEmailAndStatusOrderByIdDesc(email, StoreUserStatus.REGISTERED);
    }

    public StoreUserResponse me(StoreUserSession storeUserSession) {
        storeUserRepository.findFirstByIdAndStatusOrderByIdDesc(storeUserSession.getId(), StoreUserStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(StoreUserErrorCode.USER_NOT_FOUND));

        return StoreUserResponse.builder()
                .id(storeUserSession.getId())
                .role(storeUserSession.getRole())
                .build();
    }

}
