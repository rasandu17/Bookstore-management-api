package com.bookstore;

import com.bookstore.resource.*;
import com.bookstore.exception.*;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;



@ApplicationPath("/api")
public class Application extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {
        // Create a new HashSet to hold all our API classes
        Set<Class<?>> classes = new HashSet<>();

        // Add all the resource classes (our API endpoints)
        classes.add(AuthorResource.class);
        classes.add(BookResource.class);
        classes.add(CartResource.class);
        classes.add(CustomerResource.class);
        classes.add(OrderResource.class);

        // Add exception mapper classes
        // These handle errors and convert them to nice HTTP responses
        classes.add(ExceptionMappers.class);

        // These are our custom exceptions
        classes.add(AuthorNotFoundException.class);
        classes.add(BookNotFoundException.class);
        classes.add(CartNotFoundException.class);
        classes.add(CustomerNotFoundException.class);
        classes.add(OrderNotFoundException.class);
        classes.add(InvalidInputException.class);
        classes.add(OutOfStockException.class);

        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        // This is for objects that should only be created once
        Set<Object> singletons = new HashSet<>();
        return singletons;
    }
}