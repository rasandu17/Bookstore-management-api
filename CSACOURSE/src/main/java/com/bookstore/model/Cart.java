package com.bookstore.model;

import java.util.List;

public class Cart {
    private int customerId;
    private List<CartItem> items;


    public Cart(int customerId, List<CartItem> items) {
        this.customerId = customerId;
        this.items = items;
    }

    public int getCustomerId() {
        return customerId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void calculateTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        System.out.println("Total amount: " + total);
    }


    public void addItem(CartItem item) {
        for (CartItem existingItem : items) {
            if (existingItem.getBookId() == item.getBookId()) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public void updateItemQuantity(int bookId, int quantity) {
        for (CartItem item : items) {
            if (item.getBookId() == bookId) {
                item.setQuantity(quantity);
                return;
            }
        }
    }

    public void removeItem(int bookId) {
        items.removeIf(item -> item.getBookId() == bookId);
    }

    public void clearCart() {
        items.clear();
    }
}