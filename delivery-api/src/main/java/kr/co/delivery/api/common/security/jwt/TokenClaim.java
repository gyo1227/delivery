package kr.co.delivery.api.common.security.jwt;

import kr.co.delivery.common.security.jwt.JwtClaims;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenClaim implements JwtClaims {

    private final Map<String, ?> claims;

    public static TokenClaim of(Long userId, String role) {
        Map<String, Object> claims = Map.of(
                "id", userId.toString(),
                "role", role
        );
        return new TokenClaim(claims);
    }

    @Override
    public Map<String, ?> getClaims() {
        return claims;
    }
}
