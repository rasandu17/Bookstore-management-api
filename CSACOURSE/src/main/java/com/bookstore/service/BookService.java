package com.bookstore.service;

import com.bookstore.model.Book;
import com.bookstore.exception.BookNotFoundException;

import java.util.*;

public class BookService {

    private static final Map<Integer, Book> bookData = new HashMap<>();
    private static int nextBookId = 1;

    // Add a new book
    public Book addBook(Book book) {
        book.setBookId(nextBookId++);
        bookData.put(book.getBookId(), book);
        return book;
    }

    // Get all books
    public List<Book> getAllBooks() {
        return new ArrayList<>(bookData.values());
    }

    // Get a book by ID
    public Book getBookById(int id) {
        Book book = bookData.get(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return book;
    }

    // Update a book
    public Book updateBook(int id, Book updatedBook) {
        if (!bookData.containsKey(id)) {
            throw new BookNotFoundException(id);
        }
        updatedBook.setBookId(id);
        bookData.put(id, updatedBook);
        return updatedBook;
    }

    // Delete a book
    public void deleteBook(int id) {
        if (bookData.remove(id) == null) {
            throw new BookNotFoundException(id);
        }
    }

    // Optional: check stock availability
    public boolean isInStock(int bookId, int requiredQuantity) {
        Book book = getBookById(bookId);
        return book.getStock() >= requiredQuantity;
    }

    // Optional: reduce stock after order
    public void reduceStock(int bookId, int quantity) {
        Book book = getBookById(bookId);
        if (book.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock for book ID: " + bookId);
        }
        book.setStock(book.getStock() - quantity);
    }

    // Optional: increase stock (e.g., for returns)
    public void increaseStock(int bookId, int quantity) {
        Book book = getBookById(bookId);
        book.setStock(book.getStock() + quantity);
    }

    // Expose internal data for testing or linking with resources
    public static Map<Integer, Book> getBookData() {
        return bookData;
    }
}
