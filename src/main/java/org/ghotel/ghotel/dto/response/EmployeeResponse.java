package org.ghotel.ghotel.dto.response;

public record EmployeeResponse(
        Long id,
        String username,
        String firstName,
        String lastName
) {
}
