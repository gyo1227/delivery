package kr.co.delivery.db.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Objects;

@Repository
public class RefreshTokenRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public RefreshTokenRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String key(KeyType keyType, Long id) {
        return keyType.getPrefix() + ":refresh_token:" + id;
    }

    public void save(KeyType keyType, Long id, String token) {
        redisTemplate.opsForValue().set(key(keyType, id), token, Duration.ofDays(7));
    }

    public String get(KeyType keyType, Long id) {
        return Objects.requireNonNull(redisTemplate.opsForValue().get(key(keyType, id))).toString();
    }

    public void delete(KeyType keyType, Long id) {
        redisTemplate.delete(key(keyType, id));
    }
}
