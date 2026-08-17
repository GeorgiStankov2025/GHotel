package org.ghotel.ghotel.dto.response;

import java.util.List;
import java.util.UUID;

public record RoomReservationsResponseDTO(
        RoomResponseDTO room,
        List<ReservationResponseDTO> reservations
) {
    public UUID roomId() {
        return room != null ? room.id() : null;
    }
}
