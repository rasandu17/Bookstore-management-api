package com.bookstore.exception;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(int authorId) {
        super("Author with ID " + authorId + " does not exist.");
    }

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
