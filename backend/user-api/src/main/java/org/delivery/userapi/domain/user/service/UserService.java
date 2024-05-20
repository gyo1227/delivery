package org.delivery.userapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.error.UserErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.user.UserEntity;
import org.delivery.db.user.UserRepository;
import org.delivery.db.user.enums.UserRole;
import org.delivery.db.user.enums.UserStatus;
import org.delivery.userapi.domain.token.model.TokenResponse;
import org.delivery.userapi.domain.token.service.TokenService;
import org.delivery.userapi.domain.user.controller.model.UserLoginRequest;
import org.delivery.userapi.domain.user.controller.model.UserRegisterRequest;
import org.delivery.userapi.domain.user.controller.model.UserResponse;
import org.delivery.userapi.domain.user.model.UserSession;
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
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserConverter userConverter;
    private final UserRepository userRepository;

    private final TokenService tokenService;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$");

    public UserResponse register(UserRegisterRequest request) {
        var entity = Optional.ofNullable(request)
                .map(it -> {

                    Matcher matcher = EMAIL_PATTERN.matcher(it.getEmail());
                    if (!matcher.matches()) {
                        throw new ApiException(UserErrorCode.VALIDATED_ERROR, "이메일 형식에 맞지않습니다.");
                    }

                    userRepository.findByEmail(it.getEmail())
                            .ifPresent(userEntity -> {
                                throw new ApiException(UserErrorCode.DUPLICATED_ERROR, "중복된 이메일입니다.");
                            });

                    return UserEntity.builder()
                            .email(it.getEmail())
                            .password(passwordEncoder.encode(it.getPassword()))
                            .name(it.getName())
                            .status(UserStatus.REGISTERED)
                            .role(UserRole.USER_ROLE)
                            .address(it.getAddress())
                            .registeredAt(LocalDateTime.now())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest is Null"));

        var savedEntity = userRepository.save(entity);
        var response = userConverter.toResponse(savedEntity);

        return response;
    }

    public TokenResponse login(UserLoginRequest request) {
        userRepository.findFirstByEmailOrderByIdDesc(request.getEmail())
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var response = tokenService.issueToken(authentication);
        return response;
    }

    public Optional<UserEntity> getRegisterUser(String email) {
        return userRepository.findFirstByEmailAndStatusOrderByIdDesc(email, UserStatus.REGISTERED);
    }

    public UserResponse me(UserSession userSession) {
        userRepository.findFirstByIdAndStatusOrderByIdDesc(userSession.getId(), UserStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        return UserResponse.builder()
                .id(userSession.getId())
                .role(userSession.getRole())
                .build();
    }

}
