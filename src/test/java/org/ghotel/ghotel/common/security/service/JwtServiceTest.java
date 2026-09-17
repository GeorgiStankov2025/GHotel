package org.ghotel.ghotel.common.security.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.ghotel.ghotel.dto.response.AuthResponseDTO;
import org.ghotel.ghotel.exception.InvalidTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {
    private final String username = "TestUsername";
    private final String userRole = "ROLE_USER";
    private final String testKey = "a".repeat(50);
    private final JwtService jwtService = new JwtServiceImpl(testKey);
    private final String uri = "http://localhost:8084/";
    private String refreshToken;
    private String accessToken;
    private String expiringRefreshToken;

    @BeforeEach
    void setUp() {
        refreshToken = Jwts.builder()
                .subject(username)
                .claim("role", userRole)
                .claim("type", "REFRESH")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(4, ChronoUnit.DAYS)))
                .issuer(uri)
                .audience().add(uri).and()
                .signWith(Keys.hmacShaKeyFor(testKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
        accessToken = Jwts.builder()
                .subject(username)
                .claim("role", userRole)
                .claim("type", "ACCESS")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES)))
                .issuer(uri)
                .audience().add(uri).and()
                .signWith(Keys.hmacShaKeyFor(testKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
        expiringRefreshToken = Jwts.builder()
                .subject(username)
                .claim("role", userRole)
                .claim("type", "REFRESH")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(4, ChronoUnit.MINUTES)))
                .issuer(uri)
                .audience().add(uri).and()
                .signWith(Keys.hmacShaKeyFor(testKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    @Test
    void generateTokenPair() {
        AuthResponseDTO response = jwtService.generateTokenPair(username, userRole);

        String accessTokenUsername = jwtService.extractUsername(response.accessToken());
        String refreshTokenUsername = jwtService.extractUsername(response.refreshToken());
        String accessTokenRole = jwtService.extractRole(response.accessToken());
        String refreshTokenRole = jwtService.extractRole(response.refreshToken());
        Instant accessTokenIssuedTime = jwtService.extractIssuedTime(response.accessToken());
        Instant refreshTokenIssuedTime = jwtService.extractIssuedTime(response.refreshToken());
        Instant accessTokenExpiration = jwtService.extractExpiration(response.accessToken());
        Instant refreshTokenExpiration = jwtService.extractExpiration(response.refreshToken());
        String accessTokenType = jwtService.extractType(response.accessToken());
        String refreshTokenType = jwtService.extractType(response.refreshToken());
        String accessTokenIssuer = jwtService.extractIssuer(response.accessToken());
        String refreshTokenIssuer = jwtService.extractIssuer(response.refreshToken());

        assertNotNull(response);
        assertAll(
                () -> assertEquals(accessTokenUsername, username),
                () -> assertEquals(accessTokenRole, userRole),
                () -> assertEquals(10, ChronoUnit.MINUTES.between(accessTokenIssuedTime, accessTokenExpiration)),
                () -> assertEquals("ACCESS", accessTokenType),
                () -> assertEquals(accessTokenIssuer, uri)
        );
        assertAll(
                () -> assertEquals(refreshTokenUsername, username),
                () -> assertEquals(refreshTokenRole, userRole),
                () -> assertEquals(4, ChronoUnit.DAYS.between(refreshTokenIssuedTime, refreshTokenExpiration)),
                () -> assertEquals("REFRESH", refreshTokenType),
                () -> assertEquals(refreshTokenIssuer, uri)
        );
    }

    @Test
    void refreshAccessToken() {
        AuthResponseDTO response = jwtService.refreshAccessToken(refreshToken);

        String accessTokenUsername = jwtService.extractUsername(response.accessToken());
        String userTokenUsername = jwtService.extractUsername(refreshToken);
        String accessTokenRole = jwtService.extractRole(response.accessToken());
        String userTokenRole = jwtService.extractRole(refreshToken);
        Instant accessTokenIssuedTime = jwtService.extractIssuedTime(response.accessToken());
        Instant accessTokenExpiration = jwtService.extractExpiration(response.accessToken());
        String accessTokenType = jwtService.extractType(response.accessToken());
        String accessTokenIssuer = jwtService.extractIssuer(response.accessToken());
        String refreshTokenIssuer = jwtService.extractIssuer(refreshToken);

        assertNotNull(response);
        assertEquals(refreshToken, response.refreshToken());
        assertAll(
                () -> assertEquals(accessTokenUsername, userTokenUsername),
                () -> assertEquals(accessTokenRole, userTokenRole),
                () -> assertEquals("ACCESS", accessTokenType),
                () -> assertEquals(10, ChronoUnit.MINUTES.between(accessTokenIssuedTime, accessTokenExpiration)),
                () -> assertEquals(accessTokenIssuer, refreshTokenIssuer)
        );
    }

    @Test
    void refreshAccessToken_ThrowsInvalidTokenException_InvalidRefreshToken() {
        InvalidTokenException ex = assertThrows(InvalidTokenException.class,
                () -> jwtService.refreshAccessToken(accessToken)
        );
        assertEquals("Token is not of appropriate type 'REFRESH'.", ex.getMessage());
    }

    @Test
    void refreshAccessToken_PreExpiry() {
        AuthResponseDTO response = jwtService.refreshAccessToken(expiringRefreshToken);
        Instant accessTokenExpiry = jwtService.extractExpiration(response.accessToken());
        Instant accessTokenIssueTime = jwtService.extractIssuedTime(response.accessToken());
        int accessTokenLifeTime = calculateTokenLifetime(accessTokenIssueTime, accessTokenExpiry);

        assertNotNull(response);
        assertTrue(accessTokenLifeTime <= 4 && accessTokenLifeTime >= 3);
    }

    private int calculateTokenLifetime(Instant start, Instant end) {
        return (int) ChronoUnit.MINUTES.between(start, end);
    }
}
