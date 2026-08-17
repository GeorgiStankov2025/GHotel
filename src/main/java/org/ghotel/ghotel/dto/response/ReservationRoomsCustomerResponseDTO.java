package org.ghotel.ghotel.dto.response;

import java.util.List;
import java.util.UUID;

public record ReservationRoomsCustomerResponseDTO(
        ReservationResponseDTO reservation,
        CustomerResponseDTO customer,
        List<RoomResponseDTO> rooms
) {
    public UUID reservationId() {
        return reservation != null ? reservation.id() : null;
    }
}
