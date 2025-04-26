package com.bookstore.resource;

import com.bookstore.model.Author;
import com.bookstore.model.Book;
import com.bookstore.exception.AuthorNotFoundException;
import com.bookstore.exception.InvalidInputException;
import com.bookstore.DataLoader;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.stream.Collectors;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private static Map<Integer, Author> authorData = new HashMap<>();
    private static int nextAuthorId = 1;

    // Simulating access to book data from BookResource
    private static Map<Integer, Book> bookData = BookResource.getBookData();

    static {
        // Preload authors from DataLoader
        List<Author> preloadedAuthors = DataLoader.getAuthors();
        for (Author author : preloadedAuthors) {
            author.setAuthorId(nextAuthorId++);
            authorData.put(author.getAuthorId(), author);
        }
    }

    // Add a new author
    @POST
    public Response addAuthor(Author author) {
        // Input validation using InvalidInputException
        if (author == null || author.getName() == null || author.getName().trim().isEmpty()) {
            throw new InvalidInputException("name", "Author name is required.");
        }
        author.setAuthorId(nextAuthorId++);
        authorData.put(author.getAuthorId(), author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    // Get all authors
    @GET
    public Response getAuthors() {
        List<Author> allAuthors = new ArrayList<>(authorData.values());
        return Response.ok(allAuthors).build();
    }

    // Get one author by ID
    @GET
    @Path("/{id}")
    public Response getAuthor(@PathParam("id") int id) {
        Author author = authorData.get(id);
        if (author == null) {
            throw new AuthorNotFoundException(id);
        }
        return Response.ok(author).build();
    }

    // Update an author
    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        // Input validation using InvalidInputException
        if (updatedAuthor == null || updatedAuthor.getName() == null || updatedAuthor.getName().trim().isEmpty()) {
            throw new InvalidInputException("name", "Author name is required.");
        }

        Author existing = authorData.get(id);
        if (existing == null) {
            throw new AuthorNotFoundException(id);
        }

        updatedAuthor.setAuthorId(id);
        authorData.put(id, updatedAuthor);
        return Response.ok(updatedAuthor).build();
    }

    // Delete an author
    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        Author removed = authorData.remove(id);
        if (removed == null) {
            throw new AuthorNotFoundException(id);
        }
        return Response.noContent().build();
    }

    // Get books by author ID
    @GET
    @Path("/{id}/books")
    public Response getBooksByAuthor(@PathParam("id") int id) {
        Author author = authorData.get(id);
        if (author == null) {
            throw new AuthorNotFoundException(id);
        }

        List<Book> booksByAuthor = bookData.values().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author.getName()))
                .collect(Collectors.toList());

        return Response.ok(booksByAuthor).build();
    }

    // Optional: let other classes access author data if needed
    public static Map<Integer, Author> getAuthorData() {
        return authorData;
    }
}