package org.ghotel.ghotel.dto.response;

import java.util.List;
import java.util.UUID;

public record CustomerReservationsResponseDTO(
        CustomerResponseDTO customer,
        List<ReservationResponseDTO> reservations
) {
    public UUID customerId() {
        return customer != null ? customer.id() : null;
    }
}
