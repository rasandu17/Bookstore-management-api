package com.bookstore.service;

import com.bookstore.model.Book;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.InvalidInputException;  // Importing InvalidInputException

import java.util.*;

public class BookService {

    private static final Map<Integer, Book> bookData = new HashMap<>();
    private static int nextBookId = 1;

    // Add a new book
    public Book addBook(Book book) {
        // Input validation using InvalidInputException
        if (book == null || book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("title", "Book title is required.");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new InvalidInputException("author", "Author name is required.");
        }
        if (book.getPrice() <= 0) {
            throw new InvalidInputException("price", "Price must be a positive number.");
        }
        if (book.getStock() < 0) {
            throw new InvalidInputException("stock", "Stock cannot be negative.");
        }

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
        // Input validation using InvalidInputException
        if (updatedBook == null || updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("title", "Book title is required.");
        }
        if (updatedBook.getAuthor() == null || updatedBook.getAuthor().trim().isEmpty()) {
            throw new InvalidInputException("author", "Author name is required.");
        }
        if (updatedBook.getPrice() <= 0) {
            throw new InvalidInputException("price", "Price must be a positive number.");
        }
        if (updatedBook.getStock() < 0) {
            throw new InvalidInputException("stock", "Stock cannot be negative.");
        }

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
        // Input validation using InvalidInputException
        if (requiredQuantity <= 0) {
            throw new InvalidInputException("requiredQuantity", "Quantity must be a positive number.");
        }

        Book book = getBookById(bookId);
        return book.getStock() >= requiredQuantity;
    }

    // Optional: reduce stock after order
    public void reduceStock(int bookId, int quantity) {
        // Input validation using InvalidInputException
        if (quantity <= 0) {
            throw new InvalidInputException("quantity", "Quantity must be a positive number.");
        }

        Book book = getBookById(bookId);
        if (book.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock for book ID: " + bookId);
        }
        book.setStock(book.getStock() - quantity);
    }

    // Optional: increase stock (e.g., for returns)
    public void increaseStock(int bookId, int quantity) {
        // Input validation using InvalidInputException
        if (quantity <= 0) {
            throw new InvalidInputException("quantity", "Quantity must be a positive number.");
        }

        Book book = getBookById(bookId);
        book.setStock(book.getStock() + quantity);
    }

    // Expose internal data for testing or linking with resources
    public static Map<Integer, Book> getBookData() {
        return bookData;
    }
}
