package com.example.productcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Product Catalog API.
 * Spring Boot application demonstrating Phase 2 concepts:
 * - Spring MVC REST controllers
 * - Bean validation
 * - Global exception handling
 * - CORS configuration
 * - OpenAPI documentation
 */
@SpringBootApplication
public class ProductCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductCatalogApplication.class, args);
    }
}