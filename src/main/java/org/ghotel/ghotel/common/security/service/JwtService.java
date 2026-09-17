package org.ghotel.ghotel.common.security.service;

import org.ghotel.ghotel.dto.response.AuthResponseDTO;

import java.time.Instant;

public interface JwtService {

    AuthResponseDTO generateTokenPair(String username, String role);

    AuthResponseDTO refreshAccessToken(String refreshToken);

    String extractUsername(String token);

    String extractRole(String token);

    String extractType(String token);

    Instant extractExpiration(String token);

    Instant extractIssuedTime(String token);

    String extractIssuer(String token);
}
