package kr.co.delivery.common.security.jwt;

public interface JwtProvider {
    String generateToken(JwtClaims jwtClaims);
}
