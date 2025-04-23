package com.bookstore.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * Central class containing exception mappers for the bookstore application.
 * Maps exceptions to appropriate HTTP responses.
 */
public class ExceptionMappers {

    /**
     * Generic error response structure
     */
    public static Response buildErrorResponse(String message, int status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status);
        errorResponse.put("message", message);

        return Response
                .status(status)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * Exception mapper for AuthorNotFoundException
     */
    @Provider
    public static class AuthorNotFoundExceptionMapper implements ExceptionMapper<AuthorNotFoundException> {
        @Override
        public Response toResponse(AuthorNotFoundException e) {
            return buildErrorResponse("Author not found: " + e.getMessage(), Response.Status.NOT_FOUND.getStatusCode());
        }
    }

    /**
     * Exception mapper for BookNotFoundException
     */
    @Provider
    public static class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException> {
        @Override
        public Response toResponse(BookNotFoundException e) {
            return buildErrorResponse("Book not found: " + e.getMessage(), Response.Status.NOT_FOUND.getStatusCode());
        }
    }

    /**
     * Exception mapper for CartNotFoundException
     */
    @Provider
    public static class CartNotFoundExceptionMapper implements ExceptionMapper<CartNotFoundException> {
        @Override
        public Response toResponse(CartNotFoundException e) {
            return buildErrorResponse("Cart not found: " + e.getMessage(), Response.Status.NOT_FOUND.getStatusCode());
        }
    }

    /**
     * Exception mapper for CustomerNotFoundException
     */
    @Provider
    public static class CustomerNotFoundExceptionMapper implements ExceptionMapper<CustomerNotFoundException> {
        @Override
        public Response toResponse(CustomerNotFoundException e) {
            return buildErrorResponse("Customer not found: " + e.getMessage(), Response.Status.NOT_FOUND.getStatusCode());
        }
    }

    /**
     * Exception mapper for OrderNotFoundException
     */
    @Provider
    public static class OrderNotFoundExceptionMapper implements ExceptionMapper<OrderNotFoundException> {
        @Override
        public Response toResponse(OrderNotFoundException e) {
            return buildErrorResponse("Order not found: " + e.getMessage(), Response.Status.NOT_FOUND.getStatusCode());
        }
    }

    /**
     * Exception mapper for InvalidInputException
     */
    @Provider
    public static class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException> {
        @Override
        public Response toResponse(InvalidInputException e) {
            return buildErrorResponse("Invalid input: " + e.getMessage(), Response.Status.BAD_REQUEST.getStatusCode());
        }
    }

    /**
     * Exception mapper for OutOfStockException
     */
    @Provider
    public static class OutOfStockExceptionMapper implements ExceptionMapper<OutOfStockException> {
        @Override
        public Response toResponse(OutOfStockException e) {
            return buildErrorResponse("Item out of stock: " + e.getMessage(), Response.Status.CONFLICT.getStatusCode());
        }
    }

    /**
     * Generic exception mapper for unhandled exceptions
     */
    @Provider
    public static class GenericExceptionMapper implements ExceptionMapper<Exception> {
        @Override
        public Response toResponse(Exception e) {
            return buildErrorResponse("An unexpected error occurred: " + e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
    }
}