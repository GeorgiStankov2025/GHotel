package org.ghotel.ghotel.dto.response;

import java.util.List;

public record CustomerReservationsResponseDTO(
        CustomerResponseDTO customer,
        List<ReservationResponseDTO> reservations
) {
}
