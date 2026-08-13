package org.ghotel.ghotel.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ReservationResponseDTO(
        UUID id,
        String details,
        OffsetDateTime checkIn,
        OffsetDateTime checkOut
        ) {
}
