package com.bookstore.service;

import com.bookstore.model.Author;
import com.bookstore.exception.AuthorNotFoundException;
import com.bookstore.exception.InvalidInputException;  // Importing InvalidInputException

import java.util.*;

public class AuthorService {

    private static final Map<Integer, Author> authorData = new HashMap<>();
    private static int nextAuthorId = 1;

    // Add a new author
    public Author addAuthor(Author author) {
        // Input validation using InvalidInputException
        if (author == null || author.getName() == null || author.getName().trim().isEmpty()) {
            throw new InvalidInputException("name", "Author name is required.");
        }

        author.setAuthorId(nextAuthorId++);
        authorData.put(author.getAuthorId(), author);
        return author;
    }

    // Get all authors
    public List<Author> getAllAuthors() {
        return new ArrayList<>(authorData.values());
    }

    // Get an author by ID
    public Author getAuthorById(int id) {
        Author author = authorData.get(id);
        if (author == null) {
            throw new AuthorNotFoundException(id);
        }
        return author;
    }

    // Update an author
    public Author updateAuthor(int id, Author updatedAuthor) {
        // Input validation using InvalidInputException
        if (updatedAuthor == null || updatedAuthor.getName() == null || updatedAuthor.getName().trim().isEmpty()) {
            throw new InvalidInputException("name", "Author name is required.");
        }

        if (!authorData.containsKey(id)) {
            throw new AuthorNotFoundException(id);
        }
        updatedAuthor.setAuthorId(id);
        authorData.put(id, updatedAuthor);
        return updatedAuthor;
    }

    // Delete an author
    public void deleteAuthor(int id) {
        if (authorData.remove(id) == null) {
            throw new AuthorNotFoundException(id);
        }
    }

    // Expose internal data if needed
    public static Map<Integer, Author> getAuthorData() {
        return authorData;
    }
}
