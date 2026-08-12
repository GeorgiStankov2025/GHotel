package org.ghotel.ghotel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeeRequestDTO(
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 25, message = "Username can be between 3 and 25 characters long")
        String username,
        @NotBlank(message = "First name is required")
        @Size(min = 3, max = 30, message = "First name can be between 3 and 30 characters long")
        String firstName,
        @NotBlank(message = "Last name is required")
        @Size(min = 3, max = 30, message = "Last name can be between 3 and 30 characters long")
        String lastName,
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 15, message = "Password can be between 8 and 15 characters long")
        String password
) {
}
