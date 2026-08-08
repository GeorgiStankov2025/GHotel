package org.ghotel.ghotel.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record CustomerRoomsResponse(
        Long id,
        String firstName,
        String lastName,
        String details,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        int stayDuration,
        List<RoomResponse> rooms
) {
}
