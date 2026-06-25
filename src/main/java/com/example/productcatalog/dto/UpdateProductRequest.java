package com.example.productcatalog.dto;

import com.example.productcatalog.model.ProductCategory;
import com.example.productcatalog.model.ProductStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Data Transfer Object for updating an existing product.
 * All fields are optional to support partial updates.
 */
public class UpdateProductRequest {

    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-_]+$", message = "Product name can only contain letters, numbers, spaces, hyphens, and underscores")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have at most 2 decimal places")
    private BigDecimal price;

    private ProductCategory category;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    private ProductStatus status;

    // Default constructor
    public UpdateProductRequest() {}

    // Constructor with all fields
    public UpdateProductRequest(String name, String description, BigDecimal price, 
                               ProductCategory category, Integer stockQuantity, ProductStatus status) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.status = status;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    /**
     * Check if any field has been provided for update
     */
    public boolean hasUpdates() {
        return name != null || description != null || price != null || 
               category != null || stockQuantity != null || status != null;
    }

    @Override
    public String toString() {
        return "UpdateProductRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", stockQuantity=" + stockQuantity +
                ", status=" + status +
                '}';
    }
}