package com.bookstore.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(int orderId) {
        super("Order with ID " + orderId + " does not exist.");
    }

    public OrderNotFoundException(String message) {
        super(message);
    }

}
