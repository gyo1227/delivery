package kr.co.delivery.api.user.auth.service;

import kr.co.delivery.api.common.security.jwt.AccessTokenProvider;
import kr.co.delivery.api.common.security.jwt.Jwts;
import kr.co.delivery.api.common.security.jwt.RefreshTokenProvider;
import kr.co.delivery.api.common.security.jwt.TokenClaim;
import kr.co.delivery.db.domains.user.domain.UserEntity;
import kr.co.delivery.db.redis.KeyType;
import kr.co.delivery.db.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtService {

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public Jwts createToken(UserEntity userEntity) {
        String accessToken = accessTokenProvider.generateToken(TokenClaim.of(userEntity.getId(), userEntity.getRole().getRole()));
        String refreshToken = refreshTokenProvider.generateToken(TokenClaim.of(userEntity.getId(), userEntity.getRole().getRole()));

        refreshTokenRepository.save(KeyType.USER, userEntity.getId(), refreshToken);
        return Jwts.of(accessToken, refreshToken);
    }
}
