package org.ghotel.ghotel.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ReservationRequestDTO(
        @NotNull(message = "Invalid data")
        UUID customerId,
        @NotNull(message = "Check in date is required")
        OffsetDateTime checkIn,
        @NotNull(message = "Check out date is required")
        OffsetDateTime checkOut,
        @Size(max = 255)
        String details
) {
}
