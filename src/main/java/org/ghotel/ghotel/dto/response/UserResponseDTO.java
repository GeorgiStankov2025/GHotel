package org.ghotel.ghotel.dto.response;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String username,
        String firstName,
        String lastName
) {
}
