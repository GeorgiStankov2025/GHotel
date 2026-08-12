package org.ghotel.ghotel.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String firstName,
        String lastName,
        String details,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        int stayDuration
) {
}
