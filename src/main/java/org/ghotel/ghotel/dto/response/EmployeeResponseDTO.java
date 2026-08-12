package org.ghotel.ghotel.dto.response;

import java.util.UUID;

public record EmployeeResponseDTO(
        UUID id,
        String username,
        String firstName,
        String lastName
) {
}
