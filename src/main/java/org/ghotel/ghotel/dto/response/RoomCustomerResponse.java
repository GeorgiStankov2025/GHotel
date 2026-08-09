package org.ghotel.ghotel.dto.response;

import org.ghotel.ghotel.entity.Customer;

public record RoomCustomerResponse(
        Long id,
        int roomCapacity,
        Long customerId,
        String customerName
) {
}
