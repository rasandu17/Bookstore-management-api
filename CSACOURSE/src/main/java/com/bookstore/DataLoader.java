package com.bookstore;

import com.bookstore.model.Author;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    private static final List<Author> authors = new ArrayList<>();

    static {
        loadAuthors();
    }

    private static void loadAuthors() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = DataLoader.class.getClassLoader().getResourceAsStream("authors.json");
            if (inputStream != null) {
                List<Author> loadedAuthors = mapper.readValue(inputStream, new TypeReference<List<Author>>() {});
                authors.addAll(loadedAuthors);
            } else {
                throw new RuntimeException("authors.json not found in resources");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load authors data", e);
        }
    }

    public static List<Author> getAuthors() {
        return authors;
    }
}