package kr.co.delivery.db.redis;

import kr.co.delivery.db.config.ContainersTestRedisConfig;
import kr.co.delivery.db.config.redis.RedisConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnableAutoConfiguration
@ContextConfiguration(classes = {RedisConfig.class, RefreshTokenRepository.class})
@DataRedisTest
class RefreshTokenRepositoryTest extends ContainersTestRedisConfig {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @DisplayName("토큰 저장")
    @Test
    void test1() throws Exception {
        // given
        KeyType keyType = KeyType.USER;
        Long id = 1L;
        String token = "refreshToken";

        // when
        refreshTokenRepository.save(keyType, id, token);

        // then
        String result = refreshTokenRepository.get(keyType, id);
        assertEquals(token, result);
    }
}