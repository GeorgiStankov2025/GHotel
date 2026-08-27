package org.ghotel.ghotel.common.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtils {

    private final SecretKey key;

    private final String path = "http://localhost:8084/";

    public JwtUtils(@Value("${JWT_SECRET}") String jwtSecret) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, String role, long minutes) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("type", "ACCESS")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(minutes, ChronoUnit.MINUTES)))
                .issuer(path)
                .audience().add(path).and()
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("type", "REFRESH")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(4, ChronoUnit.DAYS)))
                .issuer(path)
                .audience().add(path).and()
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

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public String extractType(String token) {
        return getClaims(token).get("type", String.class);
    }

    public Instant extractExpiration(String token) {
        return getClaims(token).getExpiration().toInstant();
    }
}
