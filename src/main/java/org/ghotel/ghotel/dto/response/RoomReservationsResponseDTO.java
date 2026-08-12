package org.ghotel.ghotel.dto.response;

import java.util.List;

public record RoomReservationsResponseDTO(
        RoomResponseDTO room,
        List<ReservationResponseDTO> reservations
) {
}
