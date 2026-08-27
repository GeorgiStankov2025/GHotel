package org.ghotel.ghotel.dto.response;

public record AuthResponseDTO(
        String accessToken,
        String refreshToken
) {
}