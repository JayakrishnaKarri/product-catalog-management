package com.example.productcatalog.exception;

/**
 * Exception thrown when attempting to create or update a product with a name that already exists.
 */
public class DuplicateProductNameException extends RuntimeException {

    public DuplicateProductNameException(String message) {
        super(message);
    }

    public DuplicateProductNameException(String message, Throwable cause) {
        super(message, cause);
    }
}