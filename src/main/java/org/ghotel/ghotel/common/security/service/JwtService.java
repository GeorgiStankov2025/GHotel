package org.ghotel.ghotel.common.security.service;

import org.ghotel.ghotel.dto.response.AuthResponseDTO;

public interface JwtService {

    AuthResponseDTO generateTokenPair(String username, String role);

    AuthResponseDTO refreshAccessToken(String refreshToken);
}
