package com.bookstore.service;

import com.bookstore.model.Customer;
import com.bookstore.exception.CustomerNotFoundException;

import java.util.*;

public class CustomerService {

    private static final Map<Integer, Customer> customerData = new HashMap<>();
    private static int nextCustomerId = 1;

    // Add a new customer
    public Customer addCustomer(Customer customer) {
        customer.setCustomerId(nextCustomerId++);
        customerData.put(customer.getCustomerId(), customer);
        return customer;
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerData.values());
    }

    // Get customer by ID
    public Customer getCustomerById(int id) {
        Customer customer = customerData.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }
        return customer;
    }

    // Update existing customer
    public Customer updateCustomer(int id, Customer updatedCustomer) {
        if (!customerData.containsKey(id)) {
            throw new CustomerNotFoundException(id);
        }
        updatedCustomer.setCustomerId(id);
        customerData.put(id, updatedCustomer);
        return updatedCustomer;
    }

    // Delete customer by ID
    public void deleteCustomer(int id) {
        if (customerData.remove(id) == null) {
            throw new CustomerNotFoundException(id);
        }
    }

    // Get direct access (e.g., for CartService, OrderService)
    public static Map<Integer, Customer> getCustomerData() {
        return customerData;
    }
}
