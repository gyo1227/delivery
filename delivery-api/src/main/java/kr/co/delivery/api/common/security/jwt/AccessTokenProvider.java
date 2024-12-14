package kr.co.delivery.api.common.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kr.co.delivery.common.security.jwt.JwtClaims;
import kr.co.delivery.common.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Component
public class AccessTokenProvider implements JwtProvider {

    private final SecretKey secretKey;
    private final Duration tokenExpiration;

    public AccessTokenProvider(
            @Value("${jwt.secret-key.access-token}") String secretKey,
            @Value("${jwt.expiration-time.access-token}") Duration tokenExpiration
    ) {
        final byte[] secretKeyBytes = Base64.getDecoder().decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
        this.tokenExpiration = tokenExpiration;
    }

    @Override
    public String generateToken(JwtClaims jwtClaims) {
        Date now = new Date();

        return Jwts.builder()
                .claims(jwtClaims.getClaims())
                .signWith(secretKey)
                .expiration(createExpireDate(now, tokenExpiration.toMillis()))
                .compact();
    }

    private Date createExpireDate(final Date now, long expirationTime) {
        return new Date(now.getTime() + expirationTime);
    }
}
