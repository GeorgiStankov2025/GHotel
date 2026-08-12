package org.ghotel.ghotel.dto.response;

import java.util.UUID;

public record RoomResponse(
        UUID id,
        int roomCapacity,
        boolean taken
) {
}
