package com.bookstore.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(int bookId) {
        super("Book with ID " + bookId + " does not exist.");
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}



