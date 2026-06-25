package com.example.productcatalog.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Product entity representing a product in the catalog.
 * Contains all product information including pricing, inventory, and metadata.
 */
public class Product {
    
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private ProductCategory category;
    private Integer stockQuantity;
    private ProductStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor
    public Product() {
        this.status = ProductStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Constructor with required fields
    public Product(String name, String description, BigDecimal price,
                   ProductCategory category, Integer stockQuantity) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
        this.updatedAt = LocalDateTime.now();
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
        this.updatedAt = LocalDateTime.now();
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Business methods
    public boolean isActive() {
        return ProductStatus.ACTIVE.equals(this.status);
    }

    public boolean isInStock() {
        return this.stockQuantity != null && this.stockQuantity > 0;
    }

    public void updateStock(Integer newQuantity) {
        this.stockQuantity = newQuantity;
        this.updatedAt = LocalDateTime.now();
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && 
               Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    // toString
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", stockQuantity=" + stockQuantity +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}