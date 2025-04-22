package com.bookstore.exception;


public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(int customerId) {
        super("Customer with ID " + customerId + " does not exist.");
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
