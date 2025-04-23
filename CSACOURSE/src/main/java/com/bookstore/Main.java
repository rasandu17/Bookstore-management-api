package com.bookstore;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    // Base URI the Grizzly HTTP server will listen on
    private static final String BASE_URI = "http://localhost:";
    private static final int DEFAULT_PORT = 8080;
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            // Get port from environment variable or use default
            int port = getPort();

            // Fix: Create a proper URI with path component to avoid default port 80 binding
            URI baseUri = UriBuilder.fromUri(BASE_URI + port + "/api").build();

            // Create resource configuration with the Application
            ResourceConfig config = ResourceConfig.forApplicationClass(Application.class);

            // Create and start HTTP server
            HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);

            LOGGER.info("BookStore API started with WADL available at " +
                    baseUri + "application.wadl");
            LOGGER.info("Server started on port " + port);
            LOGGER.info("Press CTRL+C to stop the server...");

            // Add shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                LOGGER.info("Shutting down server...");
                server.shutdownNow();
                LOGGER.info("Server stopped");
            }));

            // Keep the server running until terminated
            Thread.currentThread().join();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Server failed to start", ex);
            System.exit(1);
        }
    }


    private static int getPort() {
        String port = System.getenv("PORT");
        try {
            return port != null ? Integer.parseInt(port) : DEFAULT_PORT;
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid PORT environment variable value: " + port + ". Using default port: " + DEFAULT_PORT);
            return DEFAULT_PORT;
        }
    }
}