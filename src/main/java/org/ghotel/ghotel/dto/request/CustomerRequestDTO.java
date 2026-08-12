package org.ghotel.ghotel.dto.request;

import jakarta.validation.constraints.*;

public record CustomerRequestDTO(
        @NotBlank(message = "First name is required")
        @Size(min = 3, max = 30, message = "First name can be between 3 and 30 characters long")
        String firstName,
        @NotBlank(message = "Last name is required")
        @Size(min = 3, max = 30, message = "Last name can be between 3 and 30 characters long")
        String lastName
) {
}
