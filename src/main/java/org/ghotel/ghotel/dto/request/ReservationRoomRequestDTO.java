package org.ghotel.ghotel.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReservationRoomRequestDTO(
        @NotNull(message = "Invalid data")
        UUID reservationId,
        @NotNull(message = "Invalid data")
        UUID roomId
) {
}
