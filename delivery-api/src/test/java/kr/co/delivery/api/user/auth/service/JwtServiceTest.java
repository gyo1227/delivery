package kr.co.delivery.api.user.auth.service;

import kr.co.delivery.api.common.security.jwt.AccessTokenProvider;
import kr.co.delivery.api.common.security.jwt.Jwts;
import kr.co.delivery.api.common.security.jwt.RefreshTokenProvider;
import kr.co.delivery.api.common.security.jwt.TokenClaim;
import kr.co.delivery.api.config.fixture.UserFixture;
import kr.co.delivery.db.domains.user.domain.UserEntity;
import kr.co.delivery.db.redis.KeyType;
import kr.co.delivery.db.redis.RefreshTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private AccessTokenProvider accessTokenProvider;

    @Mock
    private RefreshTokenProvider refreshTokenProvider;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @DisplayName("토큰 생성")
    @Test
    void test1() throws Exception {
        // when
        UserEntity userEntity = UserFixture.USER.toUserEntity();
        String expectedAccessToken = "accessToken";
        String expectedRefreshToken = "refreshToken";

        given(accessTokenProvider.generateToken(any(TokenClaim.class))).willReturn(expectedAccessToken);
        given(refreshTokenProvider.generateToken(any(TokenClaim.class))).willReturn(expectedRefreshToken);

        // when
        Jwts result = jwtService.createToken(userEntity);

        // then
        assertNotNull(result);
        assertEquals(expectedAccessToken, result.accessToken());
        assertEquals(expectedRefreshToken, result.refreshToken());
        then(refreshTokenRepository).should().save(KeyType.USER, userEntity.getId(), expectedRefreshToken);
    }
}