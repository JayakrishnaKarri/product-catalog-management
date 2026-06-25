package com.example.productcatalog.repository;

import com.example.productcatalog.model.Product;
import com.example.productcatalog.model.ProductCategory;
import com.example.productcatalog.model.ProductStatus;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory repository implementation for Product entities.
 * Thread-safe implementation using ConcurrentHashMap and AtomicLong.
 */
@Repository
public class ProductRepository {

    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Save a product (create or update)
     */
    public Product save(Product product) {
        if (product.getId() == null) {
            // New product - generate ID
            product.setId(idGenerator.getAndIncrement());
        }
        products.put(product.getId(), product);
        return product;
    }

    /**
     * Find product by ID
     */
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    /**
     * Find all products
     */
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    /**
     * Check if product exists by ID
     */
    public boolean existsById(Long id) {
        return products.containsKey(id);
    }

    /**
     * Delete product by ID
     */
    public void deleteById(Long id) {
        products.remove(id);
    }

    /**
     * Count total products
     */
    public long count() {
        return products.size();
    }

    /**
     * Find products by name (case-insensitive, partial match)
     */
    public List<Product> findByNameContainingIgnoreCase(String name) {
        if (name == null || name.trim().isEmpty()) {
            return findAll();
        }
        
        String searchTerm = name.toLowerCase().trim();
        return products.values().stream()
                .filter(product -> product.getName() != null && 
                        product.getName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Find products by category
     */
    public List<Product> findByCategory(ProductCategory category) {
        return products.values().stream()
                .filter(product -> category.equals(product.getCategory()))
                .collect(Collectors.toList());
    }

    /**
     * Find products by status
     */
    public List<Product> findByStatus(ProductStatus status) {
        return products.values().stream()
                .filter(product -> status.equals(product.getStatus()))
                .collect(Collectors.toList());
    }

    /**
     * Find products by price range
     */
    public List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return products.values().stream()
                .filter(product -> {
                    BigDecimal price = product.getPrice();
                    if (price == null) return false;
                    
                    boolean aboveMin = minPrice == null || price.compareTo(minPrice) >= 0;
                    boolean belowMax = maxPrice == null || price.compareTo(maxPrice) <= 0;
                    
                    return aboveMin && belowMax;
                })
                .collect(Collectors.toList());
    }

    /**
     * Check if product name already exists (for duplicate validation)
     */
    public boolean existsByNameIgnoreCase(String name) {
        if (name == null) return false;
        
        return products.values().stream()
                .anyMatch(product -> product.getName() != null && 
                         product.getName().equalsIgnoreCase(name.trim()));
    }

    /**
     * Check if product name exists for different product (for update validation)
     */
    public boolean existsByNameIgnoreCaseAndIdNot(String name, Long id) {
        if (name == null) return false;
        
        return products.values().stream()
                .anyMatch(product -> product.getName() != null && 
                         product.getName().equalsIgnoreCase(name.trim()) &&
                         !Objects.equals(product.getId(), id));
    }

    /**
     * Find products with complex search criteria
     */
    public List<Product> findWithCriteria(String name, ProductCategory category, 
                                         ProductStatus status, BigDecimal minPrice, 
                                         BigDecimal maxPrice) {
        return products.values().stream()
                .filter(product -> {
                    // Name filter
                    if (name != null && !name.trim().isEmpty()) {
                        if (product.getName() == null || 
                            !product.getName().toLowerCase().contains(name.toLowerCase().trim())) {
                            return false;
                        }
                    }
                    
                    // Category filter
                    if (category != null && !category.equals(product.getCategory())) {
                        return false;
                    }
                    
                    // Status filter
                    if (status != null && !status.equals(product.getStatus())) {
                        return false;
                    }
                    
                    // Price range filter
                    BigDecimal price = product.getPrice();
                    if (price != null) {
                        if (minPrice != null && price.compareTo(minPrice) < 0) {
                            return false;
                        }
                        if (maxPrice != null && price.compareTo(maxPrice) > 0) {
                            return false;
                        }
                    }
                    
                    return true;
                })
                .collect(Collectors.toList());
    }

    /**
     * Clear all products (useful for testing)
     */
    public void deleteAll() {
        products.clear();
        idGenerator.set(1);
    }

    /**
     * Get products sorted by name
     */
    public List<Product> findAllOrderByName() {
        return products.values().stream()
                .sorted(Comparator.comparing(Product::getName, 
                       Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
                .collect(Collectors.toList());
    }

    /**
     * Get products sorted by price
     */
    public List<Product> findAllOrderByPrice() {
        return products.values().stream()
                .sorted(Comparator.comparing(Product::getPrice, 
                       Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }

    /**
     * Get products sorted by creation date (newest first)
     */
    public List<Product> findAllOrderByCreatedAtDesc() {
        return products.values().stream()
                .sorted(Comparator.comparing(Product::getCreatedAt, 
                       Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }
}