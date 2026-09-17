package org.ghotel.ghotel.common.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.extern.slf4j.Slf4j;
import org.ghotel.ghotel.dto.response.AuthResponseDTO;
import org.ghotel.ghotel.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j(access = AccessLevel.PRIVATE)
@Service
public class JwtServiceImpl implements JwtService {

    private final SecretKey key;

    private final String uri = "http://localhost:8084/";

    public JwtServiceImpl(@Value("${JWT_SECRET}") String jwtSecret) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public AuthResponseDTO generateTokenPair(String username, String role) {
        String accessToken = generateToken(username, role, 10);
        String refreshToken = generateRefreshToken(username, role);
        return new AuthResponseDTO(accessToken, refreshToken);
    }

    @Override
    public AuthResponseDTO refreshAccessToken(String refreshToken) {
        validateTokenType(refreshToken);
        int expiration = validateExpiration(refreshToken);
        String username = extractUsername(refreshToken);
        String role = extractRole(refreshToken);
        String accessToken = generateToken(username, role, expiration);
        return new AuthResponseDTO(accessToken, refreshToken);
    }

    private int validateExpiration(String refreshToken) {
        Instant expiration = extractExpiration(refreshToken);

        if (expiration.isBefore(Instant.now().plus(10, ChronoUnit.MINUTES))) {
            return (int) ChronoUnit.MINUTES.between(Instant.now(), expiration);
        } else {
            return 10;
        }
    }

    private void validateTokenType(String refreshToken) {
        String tokenType = extractType(refreshToken);
        if (!tokenType.equals("REFRESH")) {
            throw new InvalidTokenException("Token is not of appropriate type 'REFRESH'.");
        }
    }

    private String generateToken(String username, String role, int minutes) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("type", "ACCESS")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(minutes, ChronoUnit.MINUTES)))
                .issuer(uri)
                .audience().add(uri).and()
                .signWith(key)
                .compact();
    }

    private String generateRefreshToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("type", "REFRESH")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(4, ChronoUnit.DAYS)))
                .issuer(uri)
                .audience().add(uri).and()
                .signWith(key)
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    @Override
    public String extractType(String token) {
        return getClaims(token).get("type", String.class);
    }

    @Override
    public Instant extractExpiration(String token) {
        return getClaims(token).getExpiration().toInstant();
    }

    @Override
    public Instant extractIssuedTime(String token) {
        return getClaims(token).getIssuedAt().toInstant();
    }

    @Override
    public String extractIssuer(String token) {
        return getClaims(token).getIssuer();
    }
}
