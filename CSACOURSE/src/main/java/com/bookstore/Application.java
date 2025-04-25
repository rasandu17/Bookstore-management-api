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

        // Add exception mapper classes individually
        classes.add(ExceptionMappers.AuthorNotFoundExceptionMapper.class);
        classes.add(ExceptionMappers.BookNotFoundExceptionMapper.class);
        classes.add(ExceptionMappers.CartNotFoundExceptionMapper.class);
        classes.add(ExceptionMappers.CustomerNotFoundExceptionMapper.class);
        classes.add(ExceptionMappers.OrderNotFoundExceptionMapper.class);
        classes.add(ExceptionMappers.InvalidInputExceptionMapper.class);
        classes.add(ExceptionMappers.OutOfStockExceptionMapper.class);
        classes.add(ExceptionMappers.GenericExceptionMapper.class);

        // No need to register exception classes themselves
        // as they are not JAX-RS components

        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        // This is for objects that should only be created once
        Set<Object> singletons = new HashSet<>();
        return singletons;
    }
}