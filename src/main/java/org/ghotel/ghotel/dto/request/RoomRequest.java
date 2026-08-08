package org.ghotel.ghotel.dto.request;

import jakarta.validation.constraints.Min;

public record RoomRequest(
        @Min(value = 1, message = "Capacity should be at least 1.")
        int roomCapacity
) {
}
