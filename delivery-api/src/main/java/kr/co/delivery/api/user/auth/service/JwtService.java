package kr.co.delivery.api.user.auth.service;

import kr.co.delivery.api.common.security.jwt.AccessTokenProvider;
import kr.co.delivery.api.common.security.jwt.Jwts;
import kr.co.delivery.api.common.security.jwt.RefreshTokenProvider;
import kr.co.delivery.api.common.security.jwt.TokenClaim;
import kr.co.delivery.db.domains.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtService {

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;

    public Jwts createToken(UserEntity userEntity) {
        String accessToken = accessTokenProvider.generateToken(TokenClaim.of(userEntity.getId(), userEntity.getRole().getRole()));
        String refreshToken = refreshTokenProvider.generateToken(TokenClaim.of(userEntity.getId(), userEntity.getRole().getRole()));

        // TODO: redis에 리프레시 토큰 저장
        return Jwts.of(accessToken, refreshToken);
    }
}
