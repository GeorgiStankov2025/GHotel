package org.ghotel.ghotel.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CustomerRoomsResponse(
        UUID id,
        String firstName,
        String lastName,
        String details,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        int stayDuration,
        List<RoomResponse> rooms
) {
}
