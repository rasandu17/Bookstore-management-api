package com.bookstore.resource;

import com.bookstore.model.Order;
import com.bookstore.model.OrderItem;
import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.exception.OrderNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private static Map<Integer, Order> orderData = new HashMap<>();
    private static int nextOrderId = 1;

    // Create a new order
    @POST
    public Response createOrder(Order order) {
        // Validate the customer (ensure the customer exists)
        if (!CustomerResource.getCustomerData().containsKey(order.getCustomerId())) {
            throw new CustomerNotFoundException(order.getCustomerId());
        }

        // Calculate the total amount
        double totalAmount = 0;
        for (OrderItem item : order.getOrderItems()) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
        order.setTotalAmount(totalAmount);

        // Set the order ID and add to the order data
        order.setOrderId(nextOrderId++);
        order.setOrderDate(new Date());
        orderData.put(order.getOrderId(), order);

        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    // Get all orders
    @GET
    public Response getOrders() {
        List<Order> allOrders = new ArrayList<>(orderData.values());
        return Response.ok(allOrders).build();
    }

    // Get one order by ID
    @GET
    @Path("/{id}")
    public Response getOrder(@PathParam("id") int id) {
        Order order = orderData.get(id);
        if (order == null) {
            throw new OrderNotFoundException(id);
        }
        return Response.ok(order).build();
    }

    // Get orders by customer ID
    @GET
    @Path("/customer/{customerId}")
    public Response getOrdersByCustomer(@PathParam("customerId") int customerId) {
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : orderData.values()) {
            if (order.getCustomerId() == customerId) {
                customerOrders.add(order);
            }
        }
        if (customerOrders.isEmpty()) {
            throw new CustomerNotFoundException(customerId);
        }
        return Response.ok(customerOrders).build();
    }

    // Optional: to allow other resources to access order data
    public static Map<Integer, Order> getOrderData() {
        return orderData;
    }
}
