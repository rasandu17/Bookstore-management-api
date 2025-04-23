package com.bookstore.service;

import com.bookstore.model.Order;
import com.bookstore.model.OrderItem;
import com.bookstore.model.Book;
import com.bookstore.exception.OrderNotFoundException;
import com.bookstore.exception.OutOfStockException;

import java.util.*;

public class OrderService {

    private static final Map<Integer, Order> orders = new HashMap<>();
    private static int nextOrderId = 1;

    private final BookService bookService;

    public OrderService(BookService bookService) {
        this.bookService = bookService;
    }

    // Create a new order
    public Order createOrder(Order order) {
        double totalAmount = 0;

        // Check stock and calculate total
        for (OrderItem item : order.getOrderItems()) {
            Book book = bookService.getBookById(item.getBookId());

            if (book.getStock() < item.getQuantity()) {
                throw new OutOfStockException(item.getBookId());
            }

            totalAmount += item.getPrice() * item.getQuantity();
        }

        // Reduce stock
        for (OrderItem item : order.getOrderItems()) {
            bookService.reduceStock(item.getBookId(), item.getQuantity());
        }

        order.setOrderId(nextOrderId++);
        order.setOrderDate(new Date());
        order.setTotalAmount(totalAmount);

        orders.put(order.getOrderId(), order);
        return order;
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    // Get order by ID
    public Order getOrderById(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new OrderNotFoundException(orderId);
        }
        return order;
    }

    // Delete order by ID
    public void deleteOrder(int orderId) {
        if (orders.remove(orderId) == null) {
            throw new OrderNotFoundException(orderId);
        }
    }

    // Get orders for a specific customer
    public List<Order> getOrdersByCustomerId(int customerId) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getCustomerId() == customerId) {
                result.add(order);
            }
        }
        return result;
    }

    // Expose internal map if needed
    public static Map<Integer, Order> getOrdersData() {
        return orders;
    }
}
