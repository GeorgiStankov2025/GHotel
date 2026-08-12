package org.ghotel.ghotel.dto.response;

import java.util.List;

public record ReservationRoomsResponseDTO(
        ReservationResponseDTO reservation,
        List<RoomResponseDTO> rooms
) {
}
