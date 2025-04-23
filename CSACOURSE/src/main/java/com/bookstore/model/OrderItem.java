package com.bookstore.model;

public class OrderItem {
    private int bookId;
    private int quantity;
    private String title;
    private double price;


    public OrderItem() {
    }
    public OrderItem(int bookId, int quantity, String title, double price) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.title = title;
        this.price = price;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
