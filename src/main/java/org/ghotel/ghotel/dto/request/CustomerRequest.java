package org.ghotel.ghotel.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CustomerRequest(
        @NotBlank(message = "First name is required")
        @Size(min = 3, max = 30, message = "First name can be between 3 and 30 characters long")
        String firstName,
        @NotBlank(message = "Last name is required")
        @Size(min = 3, max = 30, message = "Last name can be between 3 and 30 characters long")
        String lastName,
        @Size(max = 255)
        String details,
        @NotNull(message = "Check in date is required")
        LocalDateTime checkIn,
        @Min(value = 1, message = "Stay duration must be one day at least.")
        int stayDuration
) {
}
