package org.delivery.userapi.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.userapi.domain.token.model.TokenDto;
import org.delivery.userapi.domain.token.model.TokenResponse;
import org.delivery.userapi.domain.user.model.UserSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;

    public TokenResponse issueToken(Authentication authentication) {
        UserSession userSession = (UserSession) authentication.getPrincipal();

        return Optional.ofNullable(userSession)
                .map(user -> {
                    var accessToken = issueAccessToken(user);
                    var refreshToken = issueRefreshToken(user);

                    Objects.requireNonNull(accessToken, () -> {
                        throw new ApiException(ErrorCode.NULL_POINT);
                    });

                    Objects.requireNonNull(refreshToken, () -> {
                        throw new ApiException(ErrorCode.NULL_POINT);
                    });

                    return TokenResponse.builder()
                            .accessToken(accessToken.getToken())
                            .accessTokenExpiredAt(accessToken.getExpiredAt())
                            .refreshToken(refreshToken.getToken())
                            .refreshTokenExpiredAt(refreshToken.getExpiredAt())
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public TokenDto issueAccessToken(UserSession userSession) {
        var data = new HashMap<String, Object>();

        var userRole = userSession.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        data.put("userId", userSession.getId());
        data.put("userRole", userRole);
        return tokenProvider.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(UserSession userSession) {
        var data = new HashMap<String, Object>();

        var userRole = userSession.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        data.put("userId", userSession.getId());
        data.put("userRole", userRole);
        return tokenProvider.issueRefreshToken(data);
    }

    public void validationAccessToken(String token) {
        var map = tokenProvider.validationToken(token);
        var userId = map.get("userId");

        Objects.requireNonNull(userId, () -> {
            throw new ApiException(ErrorCode.NULL_POINT);
        });
    }

    public Authentication getAuthentication(String token) {
        return tokenProvider.getAuthentication(token);
    }

}
