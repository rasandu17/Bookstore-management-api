package com.bookstore.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(int bookId) {
        super("Book with ID " + bookId + " is out of stock.");
    }

    public OutOfStockException(String message) {
        super(message);
    }
}
