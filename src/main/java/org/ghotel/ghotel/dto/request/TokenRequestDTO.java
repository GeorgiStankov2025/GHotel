package org.ghotel.ghotel.dto.request;

import jakarta.validation.constraints.NotNull;

public record TokenRequestDTO(
        @NotNull(message = "Token is required")
        String refreshToken
) {
}
