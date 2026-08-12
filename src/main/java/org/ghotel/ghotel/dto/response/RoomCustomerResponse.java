package org.ghotel.ghotel.dto.response;

import java.util.UUID;

public record RoomCustomerResponse(
        UUID id,
        int roomCapacity,
        UUID customerId,
        String customerName
) {
}
