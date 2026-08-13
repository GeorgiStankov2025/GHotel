package org.ghotel.ghotel.api.error;

import org.ghotel.ghotel.exception.InvalidDataException;
import org.ghotel.ghotel.exception.ResourceNotFoundException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    //ToDO: Appropriate exception handling for updated exception classes.
    // Return error messages with ProblemDetails as well as validation error messages fixes.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {

        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CustomerException.class, ResourceNotFoundException.class, InvalidDataException.class})
    public ResponseEntity<ApiError> handleNotFoundExceptions(RuntimeException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ApiError errorResponse = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Invalid data/date format.",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        ApiError errorResponse = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later.",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<String> handleOptimisticLockingFailure() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("The requested object is not modifiable at the moment.");
    }

}
