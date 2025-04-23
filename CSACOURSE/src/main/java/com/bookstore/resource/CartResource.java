package com.bookstore.resource;

import com.bookstore.model.Cart;
import com.bookstore.model.CartItem;
import com.bookstore.exception.CustomerNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    private static Map<Integer, Cart> cartData = new HashMap<>();

    // Add items to a customer's cart
    @POST
    @Path("/{customerId}/items")
    public Response addItemToCart(@PathParam("customerId") int customerId, CartItem item) {
        Cart cart = cartData.get(customerId);
        if (cart == null) {
            cart = new Cart(customerId, new ArrayList<>());
            cartData.put(customerId, cart);
        }
        cart.addItem(item);
        return Response.status(Response.Status.CREATED).entity(cart).build();
    }

    // Get a customer's cart
    @GET
    @Path("/{customerId}")
    public Response getCart(@PathParam("customerId") int customerId) {
        Cart cart = cartData.get(customerId);
        if (cart == null) {
            throw new CustomerNotFoundException(customerId);
        }
        return Response.ok(cart).build();
    }

    // Update the quantity of an item in the cart
    @PUT
    @Path("/{customerId}/items/{bookId}")
    public Response updateItemQuantity(@PathParam("customerId") int customerId,
                                       @PathParam("bookId") int bookId,
                                       @QueryParam("quantity") int quantity) {
        Cart cart = cartData.get(customerId);
        if (cart == null) {
            throw new CustomerNotFoundException(customerId);
        }
        cart.updateItemQuantity(bookId, quantity);
        return Response.ok(cart).build();
    }

    // Remove an item from the cart
    @DELETE
    @Path("/{customerId}/items/{bookId}")
    public Response removeItemFromCart(@PathParam("customerId") int customerId,
                                       @PathParam("bookId") int bookId) {
        Cart cart = cartData.get(customerId);
        if (cart == null) {
            throw new CustomerNotFoundException(customerId);
        }
        cart.removeItem(bookId);
        return Response.noContent().build();
    }

    // Clear the customer's cart
    @DELETE
    @Path("/{customerId}")
    public Response clearCart(@PathParam("customerId") int customerId) {
        Cart cart = cartData.get(customerId);
        if (cart == null) {
            throw new CustomerNotFoundException(customerId);
        }
        cart.clearCart();
        return Response.noContent().build();
    }

    // Optional: to allow other resources to access cart data
    public static Map<Integer, Cart> getCartData() {
        return cartData;
    }
}
