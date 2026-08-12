package org.ghotel.ghotel.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CustomerResponseDTO(
        UUID id,
        String firstName,
        String lastName
) {
}
