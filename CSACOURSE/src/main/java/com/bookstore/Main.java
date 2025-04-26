package com.bookstore;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final String BASE_URI = "http://localhost:";
    private static final int DEFAULT_PORT = 8080;
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Suppress verbose logging from Jersey/Grizzly
        Logger.getLogger("org.glassfish").setLevel(Level.SEVERE);
        Logger.getLogger("org.glassfish.grizzly").setLevel(Level.SEVERE);
        Logger.getLogger("org.glassfish.jersey").setLevel(Level.SEVERE);

        try {
            int port = getPort();
            URI baseUri = UriBuilder.fromUri(BASE_URI + port ).build();

            ResourceConfig config = ResourceConfig.forApplicationClass(Application.class);
            config.property("jersey.config.server.wadl.disableWadl", true); // Disable WADL

            HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);

            LOGGER.info("Server started at " + baseUri);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                LOGGER.info("Shutting down server...");
                server.shutdownNow();
                LOGGER.info("Server stopped");
            }));

            Thread.currentThread().join(); // Keep server running

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
            LOGGER.warning("Invalid PORT environment variable: " + port + ". Using default port: " + DEFAULT_PORT);
            return DEFAULT_PORT;
        }
    }
}
