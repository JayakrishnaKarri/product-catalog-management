package com.example.productcatalog.service;

import com.example.productcatalog.dto.CreateProductRequest;
import com.example.productcatalog.dto.ProductDTO;
import com.example.productcatalog.dto.UpdateProductRequest;
import com.example.productcatalog.exception.ProductNotFoundException;
import com.example.productcatalog.exception.DuplicateProductNameException;
import com.example.productcatalog.model.Product;
import com.example.productcatalog.model.ProductCategory;
import com.example.productcatalog.model.ProductStatus;
import com.example.productcatalog.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for Product operations.
 * Contains business logic and coordinates between controller and repository.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Create a new product
     */
    public ProductDTO createProduct(CreateProductRequest request) {
        // Check for duplicate name
        if (productRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateProductNameException("Product with name '" + request.getName() + "' already exists");
        }

        // Create new product
        Product product = new Product(
            request.getName(),
            request.getDescription(),
            request.getPrice(),
            request.getCategory(),
            request.getStockQuantity()
        );

        // Save and return DTO
        Product savedProduct = productRepository.save(product);
        return ProductDTO.fromProduct(savedProduct);
    }

    /**
     * Get product by ID
     */
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return ProductDTO.fromProduct(product);
    }

    /**
     * Get all products
     */
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing product
     */
    public ProductDTO updateProduct(Long id, UpdateProductRequest request) {
        // Check if product exists
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        // Check for duplicate name if name is being updated
        if (request.getName() != null && 
            !request.getName().equalsIgnoreCase(existingProduct.getName()) &&
            productRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            throw new DuplicateProductNameException("Product with name '" + request.getName() + "' already exists");
        }

        // Update fields if provided
        if (request.getName() != null) {
            existingProduct.setName(request.getName());
        }
        if (request.getDescription() != null) {
            existingProduct.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            existingProduct.setPrice(request.getPrice());
        }
        if (request.getCategory() != null) {
            existingProduct.setCategory(request.getCategory());
        }
        if (request.getStockQuantity() != null) {
            existingProduct.setStockQuantity(request.getStockQuantity());
        }
        if (request.getStatus() != null) {
            existingProduct.setStatus(request.getStatus());
        }

        // Save and return DTO
        Product updatedProduct = productRepository.save(existingProduct);
        return ProductDTO.fromProduct(updatedProduct);
    }

    /**
     * Delete a product
     */
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    /**
     * Search products by name
     */
    public List<ProductDTO> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Get products by category
     */
    public List<ProductDTO> getProductsByCategory(ProductCategory category) {
        return productRepository.findByCategory(category).stream()
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Get products by status
     */
    public List<ProductDTO> getProductsByStatus(ProductStatus status) {
        return productRepository.findByStatus(status).stream()
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Get products by price range
     */
    public List<ProductDTO> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice).stream()
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Search products with multiple criteria
     */
    public List<ProductDTO> searchProducts(String name, ProductCategory category, 
                                          ProductStatus status, BigDecimal minPrice, 
                                          BigDecimal maxPrice) {
        return productRepository.findWithCriteria(name, category, status, minPrice, maxPrice).stream()
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Get products sorted by name
     */
    public List<ProductDTO> getProductsSortedByName() {
        return productRepository.findAllOrderByName().stream()
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Get products sorted by price
     */
    public List<ProductDTO> getProductsSortedByPrice() {
        return productRepository.findAllOrderByPrice().stream()
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Get products sorted by creation date (newest first)
     */
    public List<ProductDTO> getProductsSortedByCreatedDate() {
        return productRepository.findAllOrderByCreatedAtDesc().stream()
                .map(ProductDTO::fromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Update product stock
     */
    public ProductDTO updateProductStock(Long id, Integer newStock) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        product.updateStock(newStock);
        Product updatedProduct = productRepository.save(product);
        return ProductDTO.fromProduct(updatedProduct);
    }

    /**
     * Activate product
     */
    public ProductDTO activateProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        product.setStatus(ProductStatus.ACTIVE);
        Product updatedProduct = productRepository.save(product);
        return ProductDTO.fromProduct(updatedProduct);
    }

    /**
     * Deactivate product
     */
    public ProductDTO deactivateProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        product.setStatus(ProductStatus.INACTIVE);
        Product updatedProduct = productRepository.save(product);
        return ProductDTO.fromProduct(updatedProduct);
    }

    /**
     * Discontinue product
     */
    public ProductDTO discontinueProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        product.setStatus(ProductStatus.DISCONTINUED);
        Product updatedProduct = productRepository.save(product);
        return ProductDTO.fromProduct(updatedProduct);
    }

    /**
     * Get total product count
     */
    public long getTotalProductCount() {
        return productRepository.count();
    }

    /**
     * Check if product exists
     */
    public boolean productExists(Long id) {
        return productRepository.existsById(id);
    }
}