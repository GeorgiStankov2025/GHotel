package org.ghotel.ghotel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @NotBlank(message = "Username is required")
        @Size(min = 1, max = 25, message = "Wrong username or password.")
        String username,
        @NotBlank(message = "Password is required")
        @Size(min = 1, max = 15, message = "Wrong username or password.")
        String rawPassword
) {
}
