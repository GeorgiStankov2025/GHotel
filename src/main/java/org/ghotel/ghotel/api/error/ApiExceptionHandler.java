package org.ghotel.ghotel.api.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.extern.slf4j.Slf4j;
import org.ghotel.ghotel.exception.InvalidRequestException;
import org.ghotel.ghotel.exception.ResourceNotFoundException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j(access = AccessLevel.PRIVATE)
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Validation errors."
        );
        problemDetail.setTitle("Invalid request data.");
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );
        problemDetail.setProperty("invalidFields", fieldErrors);
        String errorId = UUID.randomUUID().toString().substring(0, 8);
        log.warn("Warn [{}]: Validation violation for request [{}]: {}", errorId, ex.getObjectName(), fieldErrors);
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ProblemDetail> handleNotFoundExceptions(ResourceNotFoundException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(
                HttpStatus.NOT_FOUND
        );
        problemDetail.setTitle("Resource not found.");
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        String path = request.getRequestURI();
        String method = request.getMethod();
        String errorId = UUID.randomUUID().toString().substring(0, 8);

        log.warn("Warn [{}]: Failed to retrieve specific resource during [{}]: '{}'-{}", errorId, method, path, ex.getMessage());

        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(
                HttpStatus.BAD_REQUEST
        );
        problemDetail.setTitle("Data format error.");
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        String path = request.getRequestURI();
        String method = request.getMethod();
        String errorId = UUID.randomUUID().toString().substring(0, 8);

        log.warn("Warn [{}]: Invalid data format from HTTP request during [{}]: '{}'-{}", errorId, method, path, ex.getMessage());
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ProblemDetail> handleInvalidRequest(
            InvalidRequestException ex,
            HttpServletRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(
                HttpStatus.BAD_REQUEST
        );
        problemDetail.setTitle("Request error.");
        problemDetail.setProperty("timestamp", OffsetDateTime.now());

        String path = request.getRequestURI();
        String method = request.getMethod();
        String errorId = UUID.randomUUID().toString().substring(0, 8);

        log.warn("Warn [{}]: Unable to process data from HTTP request during [{}]: '{}'-{}", errorId, method, path, ex.getMessage());
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ProblemDetail> handleOptimisticLockingFailure(
            OptimisticLockingFailureException ex,
            HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(
                HttpStatus.CONFLICT
        );
        problemDetail.setTitle("Conflict error.");
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        String path = request.getRequestURI();
        String method = request.getMethod();
        String errorId = UUID.randomUUID().toString().substring(0, 8);

        log.warn("Warn [{}]: Cannot use requested resource from HTTP request during [{}]: '{}'-{}"
                , errorId, method, path, ex.getMessage());
        return new ResponseEntity<>(problemDetail, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleInternalError(Exception ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        problemDetail.setTitle("Conflict error.");
        problemDetail.setProperty("timestamp", OffsetDateTime.now());
        String path = request.getRequestURI();
        String method = request.getMethod();
        String errorId = UUID.randomUUID().toString().substring(0, 8);

        log.error("Error [{}]: Internal server error: [{}]: '{}'-{}",
                errorId, method, path, ex.getMessage());
        return new ResponseEntity<>(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
