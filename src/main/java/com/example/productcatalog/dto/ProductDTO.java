package com.example.productcatalog.dto;

import com.example.productcatalog.model.Product;
import com.example.productcatalog.model.ProductCategory;
import com.example.productcatalog.model.ProductStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Product responses.
 * Used to expose product data to clients while controlling serialization.
 */
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private ProductCategory category;
    private String categoryDisplayName;
    private Integer stockQuantity;
    private ProductStatus status;
    private boolean inStock;
    private boolean active;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    // Default constructor
    public ProductDTO() {}

    // Constructor from Product entity
    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.categoryDisplayName = product.getCategory() != null ? 
            product.getCategory().getDisplayName() : null;
        this.stockQuantity = product.getStockQuantity();
        this.status = product.getStatus();
        this.inStock = product.isInStock();
        this.active = product.isActive();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }

    // Static factory method
    public static ProductDTO fromProduct(Product product) {
        return new ProductDTO(product);
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
        this.categoryDisplayName = category != null ? category.getDisplayName() : null;
    }

    public String getCategoryDisplayName() {
        return categoryDisplayName;
    }

    public void setCategoryDisplayName(String categoryDisplayName) {
        this.categoryDisplayName = categoryDisplayName;
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

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", categoryDisplayName='" + categoryDisplayName + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", status=" + status +
                ", inStock=" + inStock +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}