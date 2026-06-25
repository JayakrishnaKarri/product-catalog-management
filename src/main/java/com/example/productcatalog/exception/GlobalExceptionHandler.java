package com.example.productcatalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global exception handler for the Product Catalog API.
 * Handles all exceptions and returns standardized error responses using ProblemDetails (RFC 7807).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle ProductNotFoundException
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleProductNotFoundException(
            ProductNotFoundException ex, WebRequest request) {
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND, ex.getMessage());
        
        problemDetail.setType(URI.create("https://api.productcatalog.com/errors/product-not-found"));
        problemDetail.setTitle("Product Not Found");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    /**
     * Handle DuplicateProductNameException
     */
    @ExceptionHandler(DuplicateProductNameException.class)
    public ResponseEntity<ProblemDetail> handleDuplicateProductNameException(
            DuplicateProductNameException ex, WebRequest request) {
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.CONFLICT, ex.getMessage());
        
        problemDetail.setType(URI.create("https://api.productcatalog.com/errors/duplicate-product-name"));
        problemDetail.setTitle("Duplicate Product Name");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    /**
     * Handle validation errors from @Valid
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, "Validation failed for one or more fields");
        
        problemDetail.setType(URI.create("https://api.productcatalog.com/errors/validation-failed"));
        problemDetail.setTitle("Validation Failed");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        
        // Collect validation errors
        List<Map<String, String>> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            Map<String, String> error = new HashMap<>();
            error.put("field", fieldError.getField());
            error.put("rejectedValue", fieldError.getRejectedValue() != null ? 
                     fieldError.getRejectedValue().toString() : "null");
            error.put("message", fieldError.getDefaultMessage());
            errors.add(error);
        }
        
        problemDetail.setProperty("errors", errors);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * Handle method argument type mismatch (e.g., invalid enum values)
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        
        String message = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s",
            ex.getValue(), ex.getName(), ex.getRequiredType().getSimpleName());
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, message);
        
        problemDetail.setType(URI.create("https://api.productcatalog.com/errors/invalid-parameter"));
        problemDetail.setTitle("Invalid Parameter");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("parameter", ex.getName());
        problemDetail.setProperty("rejectedValue", ex.getValue());
        problemDetail.setProperty("expectedType", ex.getRequiredType().getSimpleName());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * Handle IllegalArgumentException (e.g., invalid enum conversion)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, ex.getMessage());
        
        problemDetail.setType(URI.create("https://api.productcatalog.com/errors/illegal-argument"));
        problemDetail.setTitle("Invalid Argument");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * Handle NumberFormatException (e.g., invalid numeric values)
     */
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ProblemDetail> handleNumberFormatException(
            NumberFormatException ex, WebRequest request) {
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, "Invalid numeric format: " + ex.getMessage());
        
        problemDetail.setType(URI.create("https://api.productcatalog.com/errors/invalid-number-format"));
        problemDetail.setTitle("Invalid Number Format");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * Handle all other unexpected exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(
            Exception ex, WebRequest request) {
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        
        problemDetail.setType(URI.create("https://api.productcatalog.com/errors/internal-server-error"));
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        
        // In production, you might want to log the full exception but not expose it
        // For development, include the exception message
        problemDetail.setProperty("debugMessage", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }
}