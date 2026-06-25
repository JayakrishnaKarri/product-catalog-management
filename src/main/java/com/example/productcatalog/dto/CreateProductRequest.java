package com.example.productcatalog.dto;

import com.example.productcatalog.model.ProductCategory;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Data Transfer Object for creating a new product.
 * Contains validation rules for all required fields.
 */
public class CreateProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-_]+$", message = "Product name can only contain letters, numbers, spaces, hyphens, and underscores")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have at most 2 decimal places")
    private BigDecimal price;

    @NotNull(message = "Category is required")
    private ProductCategory category;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    // Default constructor
    public CreateProductRequest() {}

    // Constructor with all fields
    public CreateProductRequest(String name, String description, BigDecimal price, 
                               ProductCategory category, Integer stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
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

    @Override
    public String toString() {
        return "CreateProductRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}