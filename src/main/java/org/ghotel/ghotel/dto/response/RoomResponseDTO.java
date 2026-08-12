package org.ghotel.ghotel.dto.response;

import java.util.UUID;

public record RoomResponseDTO(
        UUID id,
        int roomCapacity,
        long roomNumber
) {
}
