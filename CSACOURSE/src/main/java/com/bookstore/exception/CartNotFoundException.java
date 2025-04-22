package com.bookstore.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(int cartId) {
        super("Cart with ID " + cartId + " does not exist.");
    }

    public CartNotFoundException(String message) {
        super(message);
    }
}