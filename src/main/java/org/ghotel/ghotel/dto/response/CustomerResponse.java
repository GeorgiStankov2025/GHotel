package org.ghotel.ghotel.dto.response;

import java.time.LocalDateTime;

public record CustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String details,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        int stayDuration
) {
}
