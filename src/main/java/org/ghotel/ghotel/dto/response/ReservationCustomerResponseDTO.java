package org.ghotel.ghotel.dto.response;

import java.util.List;

public record ReservationCustomerResponseDTO(
        ReservationResponseDTO reservation,
        CustomerResponseDTO customer
) {
}
