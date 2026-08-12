package org.ghotel.ghotel.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationRequestDTO(
        @NotNull(message = "Invalid data")
        UUID customerId,
        @NotNull(message = "Check in date is required")
        LocalDateTime checkIn,
        @Min(value = 1, message = "Stay duration must be one day at least.")
        @Max(value = 365, message = "Stay duration cannot exceed 365 days.")
        int stayDuration,
        @Size(max = 255)
        String details
) {
}
