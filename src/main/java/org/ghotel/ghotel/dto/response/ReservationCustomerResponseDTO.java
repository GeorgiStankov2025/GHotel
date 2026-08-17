package org.ghotel.ghotel.dto.response;

import java.util.UUID;

public record ReservationCustomerResponseDTO(
        ReservationResponseDTO reservation,
        CustomerResponseDTO customer
) {
    public UUID reservationId() {
        return reservation != null ? reservation.id() : null;
    }
}
