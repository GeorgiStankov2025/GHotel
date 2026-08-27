package org.ghotel.ghotel.common.security.service;

import lombok.AccessLevel;
import lombok.extern.slf4j.Slf4j;
import org.ghotel.ghotel.dto.response.AuthResponseDTO;
import org.ghotel.ghotel.exception.InvalidTokenException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j(access = AccessLevel.PRIVATE)
@Service
public class JwtServiceImpl implements JwtService {

    private final JwtUtils jwtUtils;

    public JwtServiceImpl(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public AuthResponseDTO generateTokenPair(String username, String role) {
        String accessToken = jwtUtils.generateToken(username, role, 10);
        String refreshToken = jwtUtils.generateRefreshToken(username, role);
        return new AuthResponseDTO(accessToken, refreshToken);
    }

    @Override
    public AuthResponseDTO refreshAccessToken(String refreshToken) {
        validateTokenType(refreshToken);
        long expiration = validateExpiration(refreshToken);
        String username = jwtUtils.extractUsername(refreshToken);
        String role = jwtUtils.extractRole(refreshToken);
        String accessToken = jwtUtils.generateToken(username, role, expiration);
        return new AuthResponseDTO(accessToken, refreshToken);
    }

    private long validateExpiration(String refreshToken) {
        Instant expiration = jwtUtils.extractExpiration(refreshToken);

        if (expiration.isBefore(Instant.now().plus(10, ChronoUnit.MINUTES))) {
            return ChronoUnit.MINUTES.between(Instant.now(), expiration);
        } else {
            return 10;
        }
    }

    private void validateTokenType(String refreshToken) {
        String tokenType = jwtUtils.extractType(refreshToken);
        if (!tokenType.equals("REFRESH")) {
            throw new InvalidTokenException("Token is not of appropriate type 'REFRESH'.");
        }
    }

}
