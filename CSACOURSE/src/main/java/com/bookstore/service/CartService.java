package com.bookstore.service;

import com.bookstore.model.Cart;
import com.bookstore.model.CartItem;
import com.bookstore.exception.CartNotFoundException;

import java.util.*;

public class CartService {

    // Key: customerId, Value: Cart
    private static final Map<Integer, Cart> cartData = new HashMap<>();

    // Create or get existing cart for customer
    public Cart getOrCreateCart(int customerId) {
        return cartData.computeIfAbsent(customerId, id -> new Cart(id, new ArrayList<>()));
    }

    // Get existing cart for customer
    public Cart getCart(int customerId) {
        Cart cart = cartData.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException(customerId);
        }
        return cart;
    }

    // Add item to cart
    public Cart addItemToCart(int customerId, CartItem item) {
        Cart cart = getOrCreateCart(customerId);
        cart.addItem(item);
        return cart;
    }

    // Update item quantity in cart
    public Cart updateItemQuantity(int customerId, int bookId, int quantity) {
        Cart cart = getCart(customerId);
        cart.updateItemQuantity(bookId, quantity);
        return cart;
    }

    // Remove item from cart
    public Cart removeItemFromCart(int customerId, int bookId) {
        Cart cart = getCart(customerId);
        cart.removeItem(bookId);
        return cart;
    }

    // Clear the cart
    public void clearCart(int customerId) {
        Cart cart = getCart(customerId);
        cart.clearCart();
    }

    // Remove the cart completely (optional)
    public void deleteCart(int customerId) {
        if (cartData.remove(customerId) == null) {
            throw new CartNotFoundException(customerId);
        }
    }

    // Expose cart data if needed
    public static Map<Integer, Cart> getCartData() {
        return cartData;
    }
}
