package com.bookstore.exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String field, String reason) {
        super("Invalid input for " + field + ": " + reason);
    }

    public InvalidInputException(String message) {
        super(message);
    }

}
