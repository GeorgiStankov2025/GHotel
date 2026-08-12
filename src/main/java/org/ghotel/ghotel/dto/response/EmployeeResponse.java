package org.ghotel.ghotel.dto.response;

import java.util.UUID;

public record EmployeeResponse(
        UUID id,
        String username,
        String firstName,
        String lastName
) {
}
