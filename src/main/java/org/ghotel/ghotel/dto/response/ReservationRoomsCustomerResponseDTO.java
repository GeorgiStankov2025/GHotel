package org.ghotel.ghotel.dto.response;

import java.util.List;

public record ReservationRoomsCustomerResponseDTO(
        ReservationResponseDTO reservation,
        CustomerResponseDTO customer,
        List<RoomResponseDTO> rooms
) {
}
