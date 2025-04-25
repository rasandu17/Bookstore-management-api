package com.bookstore.resource;

import com.bookstore.model.Customer;
import com.bookstore.exception.CustomerNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/customer")  // Changed from "/customers" to "/customer"
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private static Map<Integer, Customer> customerData = new HashMap<>();
    private static int nextCustomerId = 1;

    // Add a new customer
    @POST
    public Response addCustomer(Customer customer) {
        customer.setCustomerId(nextCustomerId++);
        customerData.put(customer.getCustomerId(), customer);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    // Get all customers
    @GET
    public Response getCustomers() {
        List<Customer> allCustomers = new ArrayList<>(customerData.values());
        return Response.ok(allCustomers).build();
    }

    // Get one customer by ID
    @GET
    @Path("/{id}")
    public Response getCustomer(@PathParam("id") int id) {
        Customer customer = customerData.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }
        return Response.ok(customer).build();
    }

    // Update a customer
    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        Customer existing = customerData.get(id);
        if (existing == null) {
            throw new CustomerNotFoundException(id);
        }

        updatedCustomer.setCustomerId(id);
        customerData.put(id, updatedCustomer);
        return Response.ok(updatedCustomer).build();
    }

    // Delete a customer
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        Customer removed = customerData.remove(id);
        if (removed == null) {
            throw new CustomerNotFoundException(id);
        }
        return Response.noContent().build();
    }

    // Optional: to allow other resources (e.g., CartResource) to access customer data
    public static Map<Integer, Customer> getCustomerData() {
        return customerData;
    }
}