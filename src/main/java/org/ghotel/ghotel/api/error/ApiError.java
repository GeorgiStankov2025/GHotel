package org.ghotel.ghotel.api.error;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(
        HttpStatus status,
        String message,
        LocalDateTime timestamp
) {
}