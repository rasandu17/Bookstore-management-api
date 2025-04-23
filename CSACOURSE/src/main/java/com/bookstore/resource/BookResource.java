package com.bookstore.resource;

import com.bookstore.model.Book;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.InvalidInputException;  // Importing InvalidInputException

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    private static Map<Integer, Book> bookData = new HashMap<>();
    private static int nextId = 1;

    public static Map<Integer, Book> getBookData() {
        return bookData;
    }

    // Add a new book
    @POST
    public Response addBook(Book book) {
        // Input validation using InvalidInputException
        if (book == null || book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("title", "Book title is required.");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new InvalidInputException("author", "Author name is required.");
        }

        book.setBookId(nextId++);
        bookData.put(book.getBookId(), book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    // Get all books
    @GET
    public Response getBooks() {
        List<Book> allBooks = new ArrayList<>(bookData.values());
        return Response.ok(allBooks).build();
    }

    // Get one book by ID
    @GET
    @Path("/{id}")
    public Response getBook(@PathParam("id") int id) {
        Book book = bookData.get(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return Response.ok(book).build();
    }

    // Update a book
    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book newBook) {
        // Input validation using InvalidInputException
        if (newBook == null || newBook.getTitle() == null || newBook.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("title", "Book title is required.");
        }
        if (newBook.getAuthor() == null || newBook.getAuthor().trim().isEmpty()) {
            throw new InvalidInputException("author", "Author name is required.");
        }

        Book existing = bookData.get(id);
        if (existing == null) {
            throw new BookNotFoundException(id);
        }

        newBook.setBookId(id);
        bookData.put(id, newBook);
        return Response.ok(newBook).build();
    }

    // Delete a book
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        Book removed = bookData.remove(id);
        if (removed == null) {
            throw new BookNotFoundException(id);
        }
        return Response.noContent().build();
    }
}
